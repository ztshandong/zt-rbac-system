package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtExcludeInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRolePostInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserPostInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtDataScopeTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtPostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRolePostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleUserInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserPostInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统 服务实现类
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtRoleInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleInfo> implements IZtRoleInfoService {

    @Override
    public String getTableName() {
        return "zt_role_info";
    }

    @Override
    protected IZtRoleInfoService getThisService() {
        return (IZtRoleInfoService) super.getThisService();
    }

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtDeptInfoService iZtDeptInfoService;

    @Autowired
    IZtPostInfoService iZtPostInfoService;

    @Autowired
    IZtUserPostInfoService iZtUserPostInfoService;

    @Autowired
    IZtRoleDeptInfoService iZtRoleDeptInfoService;

    @Autowired
    IZtRolePostInfoService iZtRolePostInfoService;

    @Autowired
    IZtRoleUserInfoService iZtRoleUserInfoService;

    @Autowired
    IZtRoleComponentInfoService iZtRoleComponentInfoService;

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.evictCaffeine(ZtCacheUtil.ALL_ROLE_INFO);
        ztCacheUtil.evictCaffeine(ZtCacheUtil.CUR_USER_ROLE_CODES + "*");
    }

    @Override
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_ROLE_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtRoleInfo> ztSimpleSelectAll() throws Exception {
        ZtRoleInfo ztRoleInfo = new ZtRoleInfo();
        ztRoleInfo.setStart(0L);
        ztRoleInfo.setLimit(99999L);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztRoleInfo);
        ztRoleInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztRoleInfoParamEntity);
        return ztRoleInfoParamEntity;
    }

    /**
     * 获取用户所有角色
     * 超级管理员有所有权限
     * <p>
     * 1.getCurUserDeptCodes
     * 2.查询部门角色，排除特定角色
     * 3.getCurUserPostCodes
     * 4.查询职位角色，排除特定角色
     * 5.查询用户角色，排除特定角色
     * 6.汇总部门角色职位角色与用户角色，并排除特定角色
     * 7.查询子角色，排除特定角色，得到最终角色
     * 包含菜单角色与数据角色
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_ROLE_CODES + "+#userInfo.userCode")
    public List<String> getCurUserAllRoleCodes(ZtUserInfo userInfo) {
        String userCode = userInfo.getUserCode();

        //所有角色编号
        Set<String> curUserAllRoleCodeSet = new HashSet<>();

        //排除的角色
        ZtExcludeInfo ztExcludeInfo = new ZtExcludeInfo();
        ztExcludeInfo.setUserCode(userCode);
        ztExcludeInfo.setExcludeType(ZtRoleStatusEnum.ROLE);
        List<ZtExcludeInfo> ztExcludeRoleList = iZtExcludeInfoService.ztSimpleGetList(ztExcludeInfo);
        List<String> ztExcludeRoleCodes = ztExcludeRoleList.stream().map(ZtExcludeInfo::getExcludeCode).distinct().collect(Collectors.toList());

        //1 部门
        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        if (curUserDeptCodes.size() > 0) {
            ZtRoleDeptInfo ztRoleDeptInfo = new ZtRoleDeptInfo();
            ZtParamEntity<ZtRoleDeptInfo> ztRoleDeptInfoParamEntity = iZtRoleDeptInfoService.getInitZtParamEntityWithOutCount(ztRoleDeptInfo);
            ztRoleDeptInfoParamEntity.andIn(ZtRoleDeptInfo::getDeptCode, curUserDeptCodes);
            ztRoleDeptInfoParamEntity = iZtRoleDeptInfoService.ztSimpleSelectProvider(ztRoleDeptInfoParamEntity);
            //2
            List<ZtRoleDeptInfo> ztRoleDeptInfoList = iZtRoleDeptInfoService.getList(ztRoleDeptInfoParamEntity);
            List<String> deptRoleCodes = ztRoleDeptInfoList.stream().map(ZtRoleDeptInfo::getRoleCode).distinct().collect(Collectors.toList());
            deptRoleCodes.removeAll(ztExcludeRoleCodes);
            curUserAllRoleCodeSet.addAll(deptRoleCodes);
        }

        //3 职位
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        if (curUserPostCodes.size() > 0) {
            ZtRolePostInfo ztRolePostInfo = new ZtRolePostInfo();
            ZtParamEntity<ZtRolePostInfo> ztRolePostInfoParamEntity = iZtRolePostInfoService.getInitZtParamEntityWithOutCount(ztRolePostInfo);
            ztRolePostInfoParamEntity.andIn(ZtRolePostInfo::getPostCode, curUserPostCodes);
            ztRolePostInfoParamEntity = iZtRolePostInfoService.ztSimpleSelectProvider(ztRolePostInfoParamEntity);
            //4
            List<ZtRolePostInfo> ztRolePostInfoList = iZtRolePostInfoService.getList(ztRolePostInfoParamEntity);
            List<String> postRoleCodes = ztRolePostInfoList.stream().map(ZtRolePostInfo::getRoleCode).distinct().collect(Collectors.toList());
            postRoleCodes.removeAll(ztExcludeRoleCodes);
            curUserAllRoleCodeSet.addAll(postRoleCodes);
        }

        //5 用户
        ZtRoleUserInfo ztRoleUserInfo = new ZtRoleUserInfo();
        ztRoleUserInfo.setUserCode(userCode);
        List<ZtRoleUserInfo> ztRoleUserInfos = iZtRoleUserInfoService.ztSimpleGetList(ztRoleUserInfo);
        List<String> userRoleCodes = ztRoleUserInfos.stream().map(ZtRoleUserInfo::getRoleCode).distinct().collect(Collectors.toList());
        userRoleCodes.removeAll(ztExcludeRoleCodes);
        curUserAllRoleCodeSet.addAll(userRoleCodes);

        //6
        curUserAllRoleCodeSet.removeAll(ztExcludeRoleCodes);

        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        //所有的角色
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //当前用户角色
        List<ZtRoleInfo> ztRoleInfoList_1 = allZtRoleList.stream().filter(t -> curUserAllRoleCodeSet.contains(t.getThisCode())).distinct().collect(Collectors.toList());

        //7
        for (ZtRoleInfo parentRole : ztRoleInfoList_1) {
            getChildRoles(parentRole, allZtRoleList, curUserAllRoleCodeSet);
        }

        //仍然还要再筛一遍
        curUserAllRoleCodeSet.removeAll(ztExcludeRoleCodes);

        //用户最终的角色
        List<String> curUserRoleCodes = new ArrayList<>(curUserAllRoleCodeSet);
        return curUserRoleCodes;
    }

    public void getChildRoles(ZtRoleInfo parentRole, List<ZtRoleInfo> allZtRoleList, Set<String> curUserAllRoleCodeSet) {
        List<ZtRoleInfo> childs = allZtRoleList.stream().filter(t -> t.getParentCode().equalsIgnoreCase(parentRole.getThisCode())).collect(Collectors.toList());
        if (childs.size() > 0) {
            List<String> collect = childs.stream().map(ZtRoleInfo::getThisCode).collect(Collectors.toList());
            curUserAllRoleCodeSet.addAll(collect);
            for (ZtRoleInfo child : childs) {
                getChildRoles(child, allZtRoleList, curUserAllRoleCodeSet);
            }
        }
    }

    /**
     * 菜单权限：向前端返回的路由
     * 1.getCurUserAllRoleCodes
     * 2.过滤菜单角色与综合角色
     * 3.查询组件角色关联表，排除特定组件
     * 4.拼装路由
     */
    public void getCurUserMenuRoles() {

    }

    /**
     * 数据权限：dataScopeDeptFlag使用
     * 当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 AND
     * 1.getCurUserAllRoleCodes
     * 2.过滤数据角色与综合角色
     * 3.ZtQueryTypeEnum是AND的DEPT_CUSTOM与getCurUserDeptCodes取交集
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES + "+#userInfo.userCode")
    public List<String> getCurUserDataRoleAndDeptCodes(ZtUserInfo userInfo) {
        //1
        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);

        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        Set<String> curUserDeptCodeSet = new HashSet<>(curUserDeptCodes);

        //2
        List<ZtRoleInfo> curUserDeptCustomAndList = allZtRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.DEPT_CUSTOM.equals(t.getDataScopeType())
                    && !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType())
                    && curUserAllRoleCodes.contains(t.getThisCode())
                    ;
        }).collect(Collectors.toList());
        //3
        for (ZtRoleInfo ztRoleInfo : curUserDeptCustomAndList) {
            curUserDeptCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }

        List<String> curUserDataRoleDeptCodes = new ArrayList<>(curUserDeptCodeSet);
        return curUserDataRoleDeptCodes;
    }

    /**
     * 数据权限：dataScopeDeptFlag使用
     * 当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 OR
     * 1.getCurUserAllRoleCodes
     * 2.过滤数据角色与综合角色
     * 3.getCurUserDeptCodes添加ZtQueryTypeEnum是OR的DEPT_CUSTOM
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES + "+#userInfo.userCode")
    public List<String> getCurUserDataRoleOrDeptCodes(ZtUserInfo userInfo) {
        //1
        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);

        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        Set<String> curUserDeptCodeSet = new HashSet<>(curUserDeptCodes);

        //2
        List<ZtRoleInfo> curUserDeptCustomOrList = allZtRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.DEPT_CUSTOM.equals(t.getDataScopeType())
                    && !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType())
                    && curUserAllRoleCodes.contains(t.getThisCode())
                    ;
        }).collect(Collectors.toList());
        //3
        for (ZtRoleInfo ztRoleInfo : curUserDeptCustomOrList) {
            curUserDeptCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }

        List<String> curUserDataRoleDeptCodes = new ArrayList<>(curUserDeptCodeSet);
        return curUserDataRoleDeptCodes;
    }

    /**
     * 数据权限：dataScopeUserFlag使用
     * 当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 AND
     * 职位最终会转换成用户
     * 1.getCurUserAllRoleCodes 获取所有角色，过滤数据角色与综合角色
     * 2.getCurUserPostCodes 获取用户所有职位
     * 3.ZtQueryTypeEnum是AND的POST_CUSTOM与getCurUserPostCodes取交集
     * 4.根据职位查询出用户 curUserDataRoleUserCodeSet
     * 5.ZtQueryTypeEnum是AND的USER_CUSTOM与curUserDataRoleUserCodeSet取交集
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES + "+#userInfo.userCode")
    public List<String> getCurUserDataRoleAndUserCodes(ZtUserInfo userInfo) {

        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> curUserAllRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //1
        curUserAllRoleList = curUserAllRoleList.stream().filter(t -> !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType()) && curUserAllRoleCodes.contains(t.getThisCode())).collect(Collectors.toList());

        //2
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        Set<String> curUserPostCodeSet = new HashSet<>(curUserPostCodes);

        //3
        List<ZtRoleInfo> curUserPostCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.POST_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserPostCustomAndList) {
            curUserPostCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }

        //4
        ZtUserPostInfo ztUserPostInfo = new ZtUserPostInfo();
        ZtParamEntity<ZtUserPostInfo> ztUserPostInfoZtParamEntity = iZtUserPostInfoService.getInitZtParamEntityWithOutCount(ztUserPostInfo);
        ztUserPostInfoZtParamEntity.andIn(ZtUserPostInfo::getPostCode, new ArrayList(curUserPostCodeSet));
        ztUserPostInfoZtParamEntity = iZtUserPostInfoService.ztSimpleSelectProviderWithoutCount(ztUserPostInfoZtParamEntity);
        List<ZtUserPostInfo> ztUserPostInfoList = iZtUserPostInfoService.getList(ztUserPostInfoZtParamEntity);
        List<String> userCodes = ztUserPostInfoList.stream().map(ZtUserPostInfo::getUserCode).collect(Collectors.toList());

        Set<String> curUserDataRoleUserCodeSet = new HashSet<>(userCodes);

        //5
        List<ZtRoleInfo> curUserCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.USER_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserCustomAndList) {
            curUserDataRoleUserCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }

        List<String> curUserDataRoleUserCodeList = new ArrayList<>(curUserDataRoleUserCodeSet);
        return curUserDataRoleUserCodeList;
    }

    /**
     * 数据权限：dataScopeUserFlag使用
     * 当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 OR
     * 职位最终会转换成用户
     * 1.getCurUserAllRoleCodes 获取所有角色，过滤数据角色与综合角色
     * 2.getCurUserPostCodes 获取用户所有职位
     * 3.getCurUserPostCodes添加ZtQueryTypeEnum是OR的POST_CUSTOM
     * 4.根据职位查询出用户 curUserDataRoleUserCodeSet
     * 5.curUserDataRoleUserCodeSet添加ZtQueryTypeEnum是OR的USER_CUSTOM
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES + "+#userInfo.userCode")
    public List<String> getCurUserDataRoleOrUserCodes(ZtUserInfo userInfo) {

        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> curUserAllRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //1
        curUserAllRoleList = curUserAllRoleList.stream().filter(t -> !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType()) && curUserAllRoleCodes.contains(t.getThisCode())).collect(Collectors.toList());

        //2
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        Set<String> curUserPostCodeSet = new HashSet<>(curUserPostCodes);

        //3
        List<ZtRoleInfo> curUserPostCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.POST_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserPostCustomAndList) {
            curUserPostCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }

        //4
        ZtUserPostInfo ztUserPostInfo = new ZtUserPostInfo();
        ZtParamEntity<ZtUserPostInfo> ztUserPostInfoZtParamEntity = iZtUserPostInfoService.getInitZtParamEntityWithOutCount(ztUserPostInfo);
        ztUserPostInfoZtParamEntity.andIn(ZtUserPostInfo::getPostCode, new ArrayList(curUserPostCodeSet));
        ztUserPostInfoZtParamEntity = iZtUserPostInfoService.ztSimpleSelectProviderWithoutCount(ztUserPostInfoZtParamEntity);
        List<ZtUserPostInfo> ztUserPostInfoList = iZtUserPostInfoService.getList(ztUserPostInfoZtParamEntity);
        List<String> userCodes = ztUserPostInfoList.stream().map(ZtUserPostInfo::getUserCode).collect(Collectors.toList());

        Set<String> curUserDataRoleUserCodeSet = new HashSet<>(userCodes);

        //5
        List<ZtRoleInfo> curUserCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.USER_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserCustomAndList) {
            curUserDataRoleUserCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }

        List<String> curUserDataRoleUserCodeList = new ArrayList<>(curUserDataRoleUserCodeSet);
        return curUserDataRoleUserCodeList;
    }
}

