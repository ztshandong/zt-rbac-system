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
     * 优先级最高，具备任意一个就会拒绝
     * 拒绝某些权限的用户，与hasPermi逻辑相反
     * 应用场景应该不多
     */
    public String[] denyPermi() default {};

    /**
     * 具备所有要求的权限
     * 优先级第二
     */
    public String[] hasAllPermi() default {};

    /**
     * 具备任意一个权限即可
     * 这个用的最多，优先级最低
     * 同时使用了hasAllPermi与hasAnyPermi会先判断hasAllPermi
     */
    public String[] hasAnyPermi() default {};

}