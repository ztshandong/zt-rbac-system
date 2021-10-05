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
@ModuleName("组件类型")
public enum ZtComponentTypeEnum implements IZtBaseEnum<ZtComponentTypeEnum> {
    MODEL(1, "模块"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮"),
    ;

    private final Integer intValue;

    private final String strValue;

    ZtComponentTypeEnum(Integer intValue, String strValue) {
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
