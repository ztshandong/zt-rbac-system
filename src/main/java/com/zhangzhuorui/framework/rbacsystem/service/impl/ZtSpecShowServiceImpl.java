package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtSpecShow;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtSpecShowService;
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
public class ZtSpecShowServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtSpecShow> implements IZtSpecShowService {

    @Override
    public String getTableName() {
        return "zt_spec_show";
    }

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.ALL_SPEC_SHOW, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCache() throws Exception {

    }

    @Override
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.ALL_SPEC_SHOW, keyGenerator = ZtCacheUtil.ALL_SPEC_SHOW + ZtCacheUtil.KEY_GENERATOR, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    public ZtParamEntity<ZtSpecShow> ztSimpleSelectAll() throws Exception {
        ZtSpecShow ztSpecShow = new ZtSpecShow();
        ztSpecShow.setStart(0L);
        ztSpecShow.setLimit(99999L);
        ZtParamEntity<ZtSpecShow> ztSpecShowZtParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztSpecShow);
        ztSpecShowZtParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztSpecShowZtParamEntity);
        return ztSpecShowZtParamEntity;
    }

}

