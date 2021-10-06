package com.zhangzhuorui.framework.rbacsystem.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :  zhangtao
 * @createDate : 2017-01-01
 * @return :
 * @description :  菜单权限注解
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZtPreAuthorize {
    /**
     * 验证用户是否具备某权限
     */
    public String hasPermi() default "";
}