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
@ModuleName("角色权限类型")
public enum ZtRoleTypeEnum implements IZtBaseEnum<ZtRoleTypeEnum> {
    COMPONENT(1, "菜单角色-读取zt_role_component_info表，控制页面"),
    DATA(2, "数据角色-读取data_scope_type字段，配合ZtDataScopeTypeEnum，控制数据"),
    MULTIPLE(3, "综合上面两种"),
    ;

    private final Integer intValue;

    private final String strValue;

    ZtRoleTypeEnum(Integer intValue, String strValue) {
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
