package com.zhangzhuorui.framework.rbacsystem.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2021/10/3 上午9:29
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@EnableCaching
@Configuration(proxyBeanMethods = true)
public class ZtCacheManager {

    public final static String CAFFEINE_CACHE = "caffeineCache";

    @Bean
    public Cache caffeineCache() {
        com.github.benmanes.caffeine.cache.Cache<Object, Object> build = Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(12, TimeUnit.HOURS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
        return new CaffeineCache(CAFFEINE_CACHE, build);
    }

    @Bean
    public org.springframework.cache.CacheManager simpleCacheManager(@Qualifier(CAFFEINE_CACHE) Cache caffeineCache) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<Cache> list = new ArrayList<>();
        list.add(caffeineCache);
        simpleCacheManager.setCaches(list);
        return simpleCacheManager;
    }

    @Bean
    public KeyGenerator allDeptInfoKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return ZtCacheUtil.ALL_DEPT_INFO;
            }
        };
    }

    @Bean
    public KeyGenerator allPostInfoKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return ZtCacheUtil.ALL_POST_INFO;
            }
        };
    }

    @Bean
    public KeyGenerator allRoleInfoKeyGenerator() {
        return (target, method, params) -> ZtCacheUtil.ALL_ROLE_INFO;
    }
}
