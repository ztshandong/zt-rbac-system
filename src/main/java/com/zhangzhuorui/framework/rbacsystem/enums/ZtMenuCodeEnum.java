package com.zhangzhuorui.framework.rbacsystem.enums;

import com.sun.tracing.dtrace.ModuleName;
import com.zhangzhuorui.framework.core.IZtBaseEnum;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@ModuleName("菜单权限")
public enum ZtMenuCodeEnum implements IZtBaseEnum<ZtMenuCodeEnum> {

    //region
    ROOT(0, "顶级模块"),
    SYSTEM_MANAGE(-1, "系统配置"),
    USER_MANAGE(-2, "用户管理"),
    DEPT_MANAGE(-3, "部门管理"),
    POST_MANAGE(-4, "职位管理"),
    ROLE_MANAGE(-5, "角色管理"),
    COMPONENT_MANAGE(-6, "组件管理"),
    BUTTON_MANAGE(-7, "按钮管理"),

    BUTTON(1, "按钮信息"),
    COMPONENT(2, "组件信息"),
    DEPT(3, "部门信息"),
    POST(4, "职位信息"),
    USER(5, "用户信息"),
    ROLE(6, "角色信息"),
    ROLE_COMPONENT(7, "角色组件关联"),
    ROLE_DEPT(8, "角色部门关联"),
    ROLE_POST(9, "角色职位关联"),
    ROLE_USER(10, "角色用户关联"),
    USER_DEPT(11, "用户部门关联"),
    USER_POST(12, "用户职位关联"),
    EXCLUDE(13, "排除的信息"),
    SPEC_HIDE(14, "特殊隐藏"),
    SPEC_SHOW(15, "特殊显示"),
    //endregion

    ;

    public static final String SYSTEM_MANAGE_CODE = "SYSTEM_MANAGE";
    public static final String USER_MANAGE_CODE = "USER_MANAGE";
    public static final String DEPT_MANAGE_CODE = "DEPT_MANAGE";
    public static final String POST_MANAGE_CODE = "POST_MANAGE";
    public static final String ROLE_MANAGE_CODE = "ROLE_MANAGE";
    public static final String COMPONENT_MANAGE_CODE = "COMPONENT_MANAGE";
    public static final String BUTTON_MANAGE_CODE = "BUTTON_MANAGE";

    public static final String BUTTON_CODE = "BUTTON";
    public static final String COMPONENT_CODE = "COMPONENT";
    public static final String DEPT_CODE = "DEPT";
    public static final String POST_CODE = "POST";
    public static final String USER_CODE = "USER";
    public static final String ROLE_CODE = "ROLE";
    public static final String ROLE_COMPONENT_CODE = "ROLE_COMPONENT";
    public static final String ROLE_DEPT_CODE = "ROLE_DEPT";
    public static final String ROLE_POST_CODE = "ROLE_POST";
    public static final String ROLE_USER_CODE = "ROLE_USER";
    public static final String USER_DEPT_CODE = "USER_DEPT";
    public static final String USER_POST_CODE = "USER_POST";
    public static final String EXCLUDE_CODE = "EXCLUDE";
    public static final String SPEC_HIDE_CODE = "SPEC_HIDE";
    public static final String SPEC_SHOW_CODE = "SPEC_SHOW";

    private final Integer intValue;

    private final String strValue;

    ZtMenuCodeEnum(Integer intValue, String strValue) {
        this.intValue = intValue;
        this.strValue = strValue;
    }

    @Override
    public Integer getIntValue() {
        return intValue;
    }

    @Override
    public String getStrValue() {
        return strValue;
    }

}
