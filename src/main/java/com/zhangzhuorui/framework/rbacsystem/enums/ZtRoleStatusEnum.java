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
@ModuleName("角色种类")
public enum ZtRoleStatusEnum implements IZtBaseEnum<ZtRoleStatusEnum> {
    COMPONENT(1, "组件"),
    DEPT(2, "部门"),
    POST(3, "职位"),
    ROLE(4, "角色"),
    USER(5, "用户"),
    ;

    private final Integer intValue;

    private final String strValue;

    ZtRoleStatusEnum(Integer intValue, String strValue) {
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
