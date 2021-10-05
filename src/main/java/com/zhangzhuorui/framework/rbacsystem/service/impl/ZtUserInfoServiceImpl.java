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

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.evictCaffeine(ZtCacheUtil.USER_INFO_BY_ID + "*");
    }

    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.USER_INFO_BY_ID + "+#userInfoFromToken.id")}
    )
    public ZtUserInfo getFullUserInfoFromToken(ZtUserInfo userInfoFromToken) {
        ZtParamEntity<ZtUserInfo> ztUserInfoZtParamEntity = getThisService().getInitZtParamEntity(userInfoFromToken);
        ztUserInfoZtParamEntity = getThisService().ztSimpleSelectByPrimaryKey(ztUserInfoZtParamEntity);
        userInfoFromToken = getThisService().getObj(ztUserInfoZtParamEntity);
        return userInfoFromToken;
    }

    @Override
    @Caching(evict =
            {@CacheEvict(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.USER_INFO_BY_ID + "+#ztParamEntity.entity.id", beforeInvocation = true)
                    , @CacheEvict(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.USER_INFO_BY_ID + "+#ztParamEntity.entity.id", beforeInvocation = false)
            }
    )
    public ZtParamEntity<ZtUserInfo> ztAfterSimpleUpdateByPrimaryKey(ZtParamEntity<ZtUserInfo> ztParamEntity) throws Exception {
        return super.ztAfterSimpleUpdateByPrimaryKey(ztParamEntity);
    }
}

