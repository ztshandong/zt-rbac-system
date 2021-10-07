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
    BUTTON(1, "按钮管理"),
    COMPONENT(2, "组件管理"),
    DEPT(3, "部门管理"),
    POST(4, "职位管理"),
    USER(5, "用户管理"),
    ROLE_COMPONENT(6, "角色组件关联"),
    ROLE_DEPT(7, "角色部门关联"),
    ROLE_POST(8, "角色职位关联"),
    ROLE_USER(9, "角色用户关联"),
    USER_DEPT(10, "用户部门关联"),
    USER_POST(11, "用户职位关联"),
    EXCLUDE(12, "排除的信息"),
    SPEC_HIDE(13, "特殊隐藏"),
    SPEC_SHOW(14, "特殊显示"),
    ;

    public static final String BUTTON_CODE = "BUTTON";
    public static final String COMPONENT_CODE = "COMPONENT";
    public static final String DEPT_CODE = "DEPT";
    public static final String POST_CODE = "POST";
    public static final String USER_CODE = "USER";
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
