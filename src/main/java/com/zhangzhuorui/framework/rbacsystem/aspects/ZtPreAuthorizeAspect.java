package com.zhangzhuorui.framework.rbacsystem.aspects;

import com.google.common.collect.Lists;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.exception.ZtPreAuthorizeException;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author :  zhangtao
 * @createDate : 2017-01-01
 * @return :
 * @description :  菜单权限控制
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Slf4j
@Aspect
@Component
public class ZtPreAuthorizeAspect {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    private HashMap<String, List<String>> denyPermissionMap = new HashMap<>();
    private HashMap<String, List<String>> anyPermissionMap = new HashMap<>();
    private HashMap<String, List<String>> allPermissionMap = new HashMap<>();

    @Around("@annotation(com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        Class targetClass = point.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ZtPreAuthorize classPermissionAnnotation = (ZtPreAuthorize) targetClass.getAnnotation(ZtPreAuthorize.class);
        ZtPreAuthorize buttonPermissionAnnotation = method.getAnnotation(ZtPreAuthorize.class);
        //类上可以不加注解，直接在方法上添加最终的权限。但是方法上不能不加
        if (buttonPermissionAnnotation == null) {
            return point.proceed();
        }
        String key = targetClass.getName() + "." + method.getName();
        log.info("ZtPreAuthorize:" + key);
        ZtUserInfo userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        Boolean curUserAdminFlag = iZtRoleInfoService.getCurUserAdminFlag(userInfo);
        if (curUserAdminFlag) {
            return point.proceed();
        }

        List<String> curUserPermission = iZtRoleInfoService.getCurUserPermission(userInfo);

        //拒绝某个权限，判断优先级最高
        List<String> denyPermissionList = denyPermissionMap.get(key);
        if (denyPermissionList == null) {
            List<String> allButtonDenyPermission = Lists.newArrayList(buttonPermissionAnnotation.denyPermi());
            Set<String> denyPermissionSet = allButtonDenyPermission.stream().filter(t -> t.contains(":")).collect(Collectors.toSet());

            if (classPermissionAnnotation != null) {
                List<String> allClassDenyPermission = Lists.newArrayList(classPermissionAnnotation.denyPermi());
                List<String> onlyButtonSelf = allButtonDenyPermission.stream().filter(t -> !StringUtils.isEmpty(t) && !t.contains(":")).collect(Collectors.toList());
                for (String buttonPermission : onlyButtonSelf) {
                    for (String classDenyPermission : allClassDenyPermission) {
                        denyPermissionSet.add(classDenyPermission + ":" + buttonPermission);
                    }
                }
            }
            denyPermissionList = new ArrayList<>(denyPermissionSet);
            denyPermissionMap.put(key, denyPermissionList);
        }
        if (denyPermissionMap.size() > 0) {
            Optional<String> denyPermission = denyPermissionList.stream().filter(curUserPermission::contains).findAny();
            if (denyPermission.isPresent()) {
                throw new ZtPreAuthorizeException(userInfo.getUserCode() + "无权访问");
            }
        }

        //满足所有要求的权限
        List<String> allPermissionList = allPermissionMap.get(key);
        if (allPermissionList == null) {
            List<String> allButtonAllPermission = Lists.newArrayList(buttonPermissionAnnotation.hasAllPermi());
            Set<String> allPermissionSet = allButtonAllPermission.stream().filter(t -> t.contains(":")).collect(Collectors.toSet());

            if (classPermissionAnnotation != null) {
                List<String> allClassAllPermission = Lists.newArrayList(classPermissionAnnotation.hasAllPermi());
                List<String> onlyButtonSelf = allButtonAllPermission.stream().filter(t -> !StringUtils.isEmpty(t) && !t.contains(":")).collect(Collectors.toList());
                for (String buttonPermission : onlyButtonSelf) {
                    for (String classAllPermission : allClassAllPermission) {
                        allPermissionSet.add(classAllPermission + ":" + buttonPermission);
                    }
                }
            }
            allPermissionList = new ArrayList<>(allPermissionSet);
            allPermissionMap.put(key, allPermissionList);
        }

        if (allPermissionList.size() > 0) {
            boolean containsAll = curUserPermission.containsAll(allPermissionList);
            if (!containsAll) {
                throw new ZtPreAuthorizeException(userInfo.getUserCode() + "无权访问");
            }
        }

        //任意满足一个即可
        List<String> anyPermissionList = anyPermissionMap.get(key);
        if (anyPermissionList == null) {
            List<String> allButtonAnyPermission = Lists.newArrayList(buttonPermissionAnnotation.hasAnyPermi());
            Set<String> anyPermissionSet = allButtonAnyPermission.stream().filter(t -> t.contains(":")).collect(Collectors.toSet());

            if (classPermissionAnnotation != null) {
                List<String> allClassAnyPermission = Lists.newArrayList(classPermissionAnnotation.hasAnyPermi());
                List<String> onlyButtonSelf = allButtonAnyPermission.stream().filter(t -> !StringUtils.isEmpty(t) && !t.contains(":")).collect(Collectors.toList());
                for (String buttonPermission : onlyButtonSelf) {
                    for (String classAnyPermission : allClassAnyPermission) {
                        anyPermissionSet.add(classAnyPermission + ":" + buttonPermission);
                    }
                }
            }
            anyPermissionList = new ArrayList<>(anyPermissionSet);
            anyPermissionMap.put(key, anyPermissionList);
        }

        if (anyPermissionList.size() > 0) {
            Optional<String> anyPermission = anyPermissionList.stream().filter(curUserPermission::contains).findAny();
            if (!anyPermission.isPresent()) {
                throw new ZtPreAuthorizeException(userInfo.getUserCode() + "无权访问");
            }
        }

        return point.proceed();
    }

}
