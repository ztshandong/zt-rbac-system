package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtExcludeInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class ZtComponentInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtComponentInfo> implements IZtComponentInfoService {

    @Override
    public String getTableName() {
        return "zt_component_info";
    }

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtRoleComponentInfoService iZtRoleComponentInfoService;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.evictCaffeine(ZtCacheUtil.ALL_COMPONENT_INFO);
        ztCacheUtil.evictCaffeine(ZtCacheUtil.CUR_USER_LEAF_COMPONENT_CODES + "*");
    }

    @Override
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_COMPONENT_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtComponentInfo> ztSimpleSelectAll() throws Exception {
        ZtComponentInfo ztComponentInfo = new ZtComponentInfo();
        ztComponentInfo.setStart(0L);
        ztComponentInfo.setLimit(99999L);
        ZtParamEntity<ZtComponentInfo> ztComponentInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztComponentInfo);
        ztComponentInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztComponentInfoParamEntity);
        return ztComponentInfoParamEntity;
    }

    /**
     * ZtRoleComponentInfo只保存末级组件，就是leafFlag=true的
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_LEAF_COMPONENT_CODES + "+#userInfo.id")}
    )
    public List<String> getCurUserLeafComponentCodes(ZtUserInfo userInfo) {
        String userCode = userInfo.getUserCode();
        //当前用户所有角色。虽然包括数据权限角色，但是数据权限在ZtRoleComponentInfo里不存在
        List<String> curUserAllRoleCodes = iZtRoleInfoService.getCurUserAllRoleCodes(userInfo);
        //角色对应的组件
        ZtRoleComponentInfo ztRoleComponentInfo = new ZtRoleComponentInfo();
        ZtParamEntity<ZtRoleComponentInfo> ztRoleDeptInfoParamEntity = iZtRoleComponentInfoService.getInitZtParamEntityWithOutCount(ztRoleComponentInfo);
        ztRoleDeptInfoParamEntity.andIn(ZtRoleComponentInfo::getRoleCode, curUserAllRoleCodes);
        ztRoleDeptInfoParamEntity = iZtRoleComponentInfoService.ztSimpleSelectProvider(ztRoleDeptInfoParamEntity);
        List<ZtRoleComponentInfo> curUserRoleComponentInfoList = iZtRoleComponentInfoService.getList(ztRoleDeptInfoParamEntity);

        List<String> curUserRoleComponentInfoCodes = curUserRoleComponentInfoList.stream().map(ZtRoleComponentInfo::getComponentCode).distinct().collect(Collectors.toList());

        //排除的组件
        ZtExcludeInfo ztExcludeInfo = new ZtExcludeInfo();
        ztExcludeInfo.setUserCode(userCode);
        ztExcludeInfo.setExcludeType(ZtRoleStatusEnum.COMPONENT);
        List<ZtExcludeInfo> ztExcludeRoleList = iZtExcludeInfoService.ztSimpleGetList(ztExcludeInfo);
        List<String> ztExcludeComponentCodes = ztExcludeRoleList.stream().map(ZtExcludeInfo::getExcludeCode).distinct().collect(Collectors.toList());
        curUserRoleComponentInfoCodes.removeAll(ztExcludeComponentCodes);

        return curUserRoleComponentInfoCodes;
        // ZtParamEntity<ZtComponentInfo> ztComponentInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        // List<ZtComponentInfo> allComponentInfoList = getThisService().getList(ztComponentInfoZtParamEntity);
        // List<ZtComponentInfo> curUserComponentInfoList = allComponentInfoList.stream().filter(t -> curUserRoleComponentInfoCodes.contains(t.getThisCode())).collect(Collectors.toList());
    }

}

