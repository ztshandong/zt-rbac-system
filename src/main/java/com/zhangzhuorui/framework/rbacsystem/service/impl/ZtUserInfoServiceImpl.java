package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ZtUserInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtUserInfo> implements IZtUserInfoService {

    @Override
    public String getTableName() {
        return "zt_user_info";
    }

    @Override
    public Boolean getManualId() {
        return true;
    }

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCache() throws Exception {

    }

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCacheByCurUserId(Long userId) {

    }

    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, key = "#userInfoFromToken.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    public ZtUserInfo getFullUserInfoFromToken(ZtUserInfo userInfoFromToken) {
        ZtParamEntity<ZtUserInfo> ztUserInfoZtParamEntity = getThisService().getInitZtParamEntity(userInfoFromToken);
        ztUserInfoZtParamEntity = getThisService().ztSimpleSelectByPrimaryKey(ztUserInfoZtParamEntity);
        userInfoFromToken = getThisService().getObj(ztUserInfoZtParamEntity);
        return userInfoFromToken;
    }

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, key = "#ztParamEntity.entity.id", beforeInvocation = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, key = "#ztParamEntity.entity.id", beforeInvocation = false, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)

                    // @CacheEvict(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.USER_INFO_BY_ID + "+#ztParamEntity.entity.id", beforeInvocation = true)
                    // , @CacheEvict(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.USER_INFO_BY_ID + "+#ztParamEntity.entity.id", beforeInvocation = false)
            }
    )
    public ZtParamEntity<ZtUserInfo> ztAfterSimpleUpdateByPrimaryKey(ZtParamEntity<ZtUserInfo> ztParamEntity) throws Exception {
        return super.ztAfterSimpleUpdateByPrimaryKey(ztParamEntity);
    }
}

