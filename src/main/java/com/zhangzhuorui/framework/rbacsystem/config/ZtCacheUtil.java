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

    public final static String ALL_DEPT_INFO = "allDeptInfo";

    public final static String KEY_GENERATOR = "KeyGenerator";

    //当前用户所属所有有效部门缓存前缀
    public final static String CUR_USER_DEPT_CODES = "'curUserDeptCodes:'";

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
