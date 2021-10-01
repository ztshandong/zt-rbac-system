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
public enum ZtButtonCodeEnum implements IZtBaseEnum<ZtButtonCodeEnum> {
    QUERY(1, "查询"),
    ADD(2, "新增"),
    EDIT(3, "编辑"),
    DEL(4, "删除"),
    APP(5, "审核"),
    UNAPP(6, "反审核"),
    UPLOAD(7, "上传"),
    DOWNLOAD(8, "下载"),
    IMPORT(9, "导入"),
    EXPORT(10, "导出"),
    PRINT(11, "打印"),
    ;

    private final Integer intValue;

    private final String strValue;

    ZtButtonCodeEnum(Integer intValue, String strValue) {
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
