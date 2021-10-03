package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
public class ZtDeptInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtDeptInfo> implements IZtDeptInfoService {

    @Override
    public String getTableName() {
        return "zt_dept_info";
    }

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Override
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_DEPT_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtDeptInfo> ztSimpleSelectAll() throws Exception {
        ZtDeptInfo ztDeptInfo = new ZtDeptInfo();
        ztDeptInfo.setStart(0L);
        ztDeptInfo.setLimit(99999L);
        ZtParamEntity<ZtDeptInfo> ztDeptInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztDeptInfo);
        ztDeptInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztDeptInfoParamEntity);
        return ztDeptInfoParamEntity;
    }

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.getCaffeineCache().evict(ZtCacheUtil.ALL_DEPT_INFO);
    }

    @Override
    public void refreshCache(String cacheName) {
        ztCacheUtil.getCaffeineCache().evict(cacheName);
    }
}

