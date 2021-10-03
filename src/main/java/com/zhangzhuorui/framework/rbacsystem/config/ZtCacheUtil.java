package com.zhangzhuorui.framework.rbacsystem.config;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Resource(name = ZtCacheManager.CAFFEINE_CACHE)
    Cache caffeineCache;

    public Cache getCaffeineCache() {
        return caffeineCache;
    }

}
