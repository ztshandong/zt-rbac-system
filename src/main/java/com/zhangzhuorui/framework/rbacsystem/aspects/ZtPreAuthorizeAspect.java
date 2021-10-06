package com.zhangzhuorui.framework.rbacsystem.aspects;

import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author :  zhangtao
 * @createDate : 2017-01-01
 * @return :
 * @description :  菜单权限注解
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

        return point.proceed();
    }

    public boolean hasPermission(String[] permissions) {
        return true;
    }
}
