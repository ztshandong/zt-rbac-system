package com.zhangzhuorui.framework.rbacsystem.config;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2021/10/3 下午12:47
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Component
public class ZtCacheUtil {

    //所有部门缓存key
    public final static String ALL_DEPT_INFO = "allDeptInfo";

    //所有职位缓存key
    public final static String ALL_POST_INFO = "allPostInfo";

    //所有角色缓存key
    public final static String ALL_ROLE_INFO = "allRoleInfo";

    //所有组件缓存key
    public final static String ALL_COMPONENT_INFO = "allComponentInfo";

    public final static String KEY_GENERATOR = "KeyGenerator";

    //用户信息详情
    public final static String CUR_USER_INFO_BY_ID = "curUserInfoById";

    //当前用户所属所有有效部门缓存前缀
    public final static String CUR_USER_DEPT_CODES = "curUserDeptCodes";

    //当前用户所属所有有效职位缓存前缀
    public final static String CUR_USER_POST_CODES = "curUserPostCodes";

    //当前用户所属所有有效角色缓存前缀
    public final static String CUR_USER_ROLE_CODES = "curUserRoleCodes";

    //当前用户是否是管理员或者有管理员的角色
    public final static String CUR_USER_ADMIN_FLAG = "curUserAdminFlag";

    //当前用户所有末级组件
    public final static String CUR_USER_LEAF_COMPONENT_CODES = "curUserLeafComponentCodes";

    //当前用户所有组件
    public final static String CUR_USER_ALL_COMPONENT_CODES = "curUserAllComponentCodes";

    //当前用户前端菜单路由
    public final static String CUR_USER_ROUTER = "curUserRouter";

    //当前用户所有按钮权限
    public final static String CUR_USER_PERMISSION = "curUserPermission";

    //当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 AND
    public final static String CUR_USER_DATA_ROLE_AND_DEPT_CODES = "curUserDataRoleAndDeptCodes";

    //当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 OR
    public final static String CUR_USER_DATA_ROLE_OR_DEPT_CODES = "curUserDataRoleOrDeptCodes";

    //当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 AND
    public final static String CUR_USER_DATA_ROLE_AND_USER_CODES = "curUserDataRoleAndUserCodes";

    //当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 OR
    public final static String CUR_USER_DATA_ROLE_OR_USER_CODES = "curUserDataRoleOrUserCodes";

    @Autowired
    CacheManager cacheManager;

    public void refreshCacheByCurUserId(Long userId) {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String name : cacheNames) {
            if (name.startsWith("curUser")) {
                Cache cache = cacheManager.getCache(name);
                cache.evict(userId);
            }
        }
    }

    public void refreshAllCache() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String name : cacheNames) {
            Cache cache = cacheManager.getCache(name);
            cache.clear();
        }
    }

    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_INFO_BY_ID, key = "#userInfoFromToken.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    public ZtUserInfo getFullUserInfoFromCache(ZtUserInfo userInfoFromToken) {
        return userInfoFromToken;
    }

}
