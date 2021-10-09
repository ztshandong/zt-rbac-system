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
@ModuleName("数据权限类型DATA_SCOPE")
public enum ZtDataScopeTypeEnum implements IZtBaseEnum<ZtDataScopeTypeEnum> {
    SELF(-2, "只能查看自己的数据"),//。如果有这个，并且是AND，就会忽略其他条件，优先级最高
    ALL(-1, "可以查看所有人的数据"),//。没有AND的SELF的前提下，如果有这个，并且是OR，会忽略其他条件，优先级次高
    DEPT_ALL(1, "用户所属部门及子部门所有数据"),
    DEPT_CUSTOM(2, "自定义部门数据"),//，不级联查询子部门。用户不属于该部门，但又要看到数据
    POST_ALL(3, "用户职位及下级职位所有数据"),//。职位最终会转换成人员账号查询
    POST_CUSTOM(4, "自定义职位数据"),//，不级联查询下级职位。用户需要查看上级职位数据
    USER_CUSTOM(5, "自定义查看指定用户的数据"),
    ;

    private final Integer intValue;

    private final String strValue;

    ZtDataScopeTypeEnum(Integer intValue, String strValue) {
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
