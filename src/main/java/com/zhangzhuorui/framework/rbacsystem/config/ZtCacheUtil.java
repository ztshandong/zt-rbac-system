package com.zhangzhuorui.framework.rbacsystem.config;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

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

    public final static String KEY_GENERATOR = "KeyGenerator";

    //当前用户所属所有有效部门缓存前缀
    public final static String CUR_USER_DEPT_CODES = "'curUserDeptCodes:'";

    //当前用户所属所有有效职位缓存前缀
    public final static String CUR_USER_POST_CODES = "'curUserPostCodes:'";

    //当前用户所属所有有效角色缓存前缀
    public final static String CUR_USER_ROLE_CODES = "'curUserRoleCodes:'";

    //当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 AND
    public final static String CUR_USER_DATA_ROLE_AND_DEPT_CODES = "'curUserDataRoleAndDeptCodes:'";

    //当前用户数据权限-可查看的部门编号缓存前缀 SQL 条件为 OR
    public final static String CUR_USER_DATA_ROLE_OR_DEPT_CODES = "'curUserDataRoleOrDeptCodes:'";

    //当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 AND
    public final static String CUR_USER_DATA_ROLE_AND_USER_CODES = "'curUserDataRoleAndUserCodes:'";

    //当前用户数据权限-可查看的用户编号缓存前缀 SQL 条件为 OR
    public final static String CUR_USER_DATA_ROLE_OR_USER_CODES = "'curUserDataRoleOrUserCodes:'";

    @Resource(name = ZtCacheManager.CAFFEINE_CACHE)
    Cache caffeineCache;

    public void evictCaffeine(String cacheName) {
        if (cacheName.endsWith("*")) {
            cacheName = cacheName.replace("*", "");
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = ((CaffeineCache) caffeineCache).getNativeCache();
            ConcurrentMap<Object, Object> cacheMap = nativeCache.asMap();
            Set<Object> cacheNames = cacheMap.keySet();
            for (Object cName : cacheNames) {
                if (((String) cName).startsWith(cacheName)) {
                    caffeineCache.evict(cName);
                }
            }
        } else {
            caffeineCache.evict(cacheName);
        }
    }

}
