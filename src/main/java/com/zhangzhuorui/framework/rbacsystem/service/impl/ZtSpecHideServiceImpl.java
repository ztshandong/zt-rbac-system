package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtSpecHide;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtSpecHideService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
public class ZtSpecHideServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtSpecHide> implements IZtSpecHideService {

    @Override
    public String getTableName() {
        return "zt_spec_hide";
    }

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.ALL_SPEC_HIDE, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCache() throws Exception {

    }

    @Override
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.ALL_SPEC_HIDE, keyGenerator = ZtCacheUtil.ALL_SPEC_HIDE + ZtCacheUtil.KEY_GENERATOR, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    public ZtParamEntity<ZtSpecHide> ztSimpleSelectAll() throws Exception {
        ZtSpecHide ztSpecHide = new ZtSpecHide();
        ztSpecHide.setStart(0L);
        ztSpecHide.setLimit(99999L);
        ZtParamEntity<ZtSpecHide> ztSpecHideZtParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztSpecHide);
        ztSpecHideZtParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztSpecHideZtParamEntity);
        return ztSpecHideZtParamEntity;
    }

}

