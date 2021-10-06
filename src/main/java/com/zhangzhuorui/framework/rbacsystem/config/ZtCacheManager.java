package com.zhangzhuorui.framework.rbacsystem.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    public final static String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    /**
     * 如果换了redis，只要改这个Bean就可以。
     * 可以讲里面的参数做成配置文件
     *
     * @param :
     * @return :  org.springframework.cache.caffeine.CaffeineCacheManager
     * @author :  zhangtao
     * @createDate :  2021/10/6 下午1:34
     * @description :
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        com.github.benmanes.caffeine.cache.Cache<Object, Object> ALL_DEPT_INFO = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.ALL_DEPT_INFO, ALL_DEPT_INFO);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> ALL_POST_INFO = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.ALL_POST_INFO, ALL_POST_INFO);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> ALL_ROLE_INFO = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.ALL_ROLE_INFO, ALL_ROLE_INFO);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> ALL_COMPONENT_INFO = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.ALL_COMPONENT_INFO, ALL_COMPONENT_INFO);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_INFO_BY_ID = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_INFO_BY_ID, CUR_USER_INFO_BY_ID);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_DEPT_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_DEPT_CODES, CUR_USER_DEPT_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_POST_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_POST_CODES, CUR_USER_POST_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_ROLE_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_ROLE_CODES, CUR_USER_ROLE_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_LEAF_COMPONENT_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_LEAF_COMPONENT_CODES, CUR_USER_LEAF_COMPONENT_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_ALL_COMPONENT_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_ALL_COMPONENT_CODES, CUR_USER_ALL_COMPONENT_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_ROUTER = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_ROUTER, CUR_USER_ROUTER);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_PERMISSION = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_PERMISSION, CUR_USER_PERMISSION);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_DATA_ROLE_AND_DEPT_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES, CUR_USER_DATA_ROLE_AND_DEPT_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_DATA_ROLE_OR_DEPT_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES, CUR_USER_DATA_ROLE_OR_DEPT_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_DATA_ROLE_AND_USER_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES, CUR_USER_DATA_ROLE_AND_USER_CODES);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> CUR_USER_DATA_ROLE_OR_USER_CODES = Caffeine.newBuilder()
                .expireAfterWrite(60 * 60 * 12, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
        caffeineCacheManager.registerCustomCache(ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES, CUR_USER_DATA_ROLE_OR_USER_CODES);
        return caffeineCacheManager;
    }

    // @Bean
    // public Cache caffeineCache() {
    //     com.github.benmanes.caffeine.cache.Cache<Object, Object> build = Caffeine.newBuilder()
    //             // 设置最后一次写入或访问后经过固定时间过期
    //             .expireAfterWrite(12, TimeUnit.HOURS)
    //             // 初始的缓存空间大小
    //             .initialCapacity(100)
    //             // 缓存的最大条数
    //             .maximumSize(1000)
    //             .build();
    //     return new CaffeineCache(CAFFEINE_CACHE, build);
    // }

    // @Bean
    // @Primary
    // public org.springframework.cache.CacheManager simpleCacheManager(@Qualifier(CAFFEINE_CACHE) Cache caffeineCache) {
    //     SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    //     List<Cache> list = new ArrayList<>();
    //     list.add(caffeineCache);
    //     simpleCacheManager.setCaches(list);
    //     return simpleCacheManager;
    // }

    /**
     * 所有部门
     *
     * @return
     */
    @Bean
    public KeyGenerator allDeptInfoKeyGenerator() {
        return (target, method, params) -> ZtCacheUtil.ALL_DEPT_INFO;
    }

    /**
     * 所有职位
     *
     * @return
     */
    @Bean
    public KeyGenerator allPostInfoKeyGenerator() {
        return (target, method, params) -> ZtCacheUtil.ALL_POST_INFO;
    }

    /**
     * 所有角色
     *
     * @return
     */
    @Bean
    public KeyGenerator allRoleInfoKeyGenerator() {
        return (target, method, params) -> ZtCacheUtil.ALL_ROLE_INFO;
    }

    /**
     * 所有组件
     *
     * @return
     */
    @Bean
    public KeyGenerator allComponentInfoKeyGenerator() {
        return (target, method, params) -> ZtCacheUtil.ALL_COMPONENT_INFO;
    }
}
