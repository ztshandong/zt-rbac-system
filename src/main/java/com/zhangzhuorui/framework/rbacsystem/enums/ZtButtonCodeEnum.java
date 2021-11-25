package com.zhangzhuorui.framework.rbacsystem.enums;

import com.zhangzhuorui.framework.core.IZtBaseEnum;
import io.swagger.annotations.ApiModel;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
// @ModuleName("按钮功能")
@ApiModel(value = "按钮功能", description = "按钮功能")
public enum ZtButtonCodeEnum implements IZtBaseEnum<ZtButtonCodeEnum> {
    QUERY(1, "查询"),
    ADD(2, "新增"),
    EDIT(3, "编辑"),
    DEL(4, "删除"),
    APP(5, "审核"),
    UNAPP(6, "反审"),
    UPLOAD(7, "上传"),
    DOWNLOAD(8, "下载"),
    IMPORT(9, "导入"),
    EXPORT(10, "导出"),
    PRINT(11, "打印"),
    ENABLE(12, "启用"),
    ;

    public static final String QUERY_CODE = "QUERY";
    public static final String ADD_CODE = "ADD";
    public static final String EDIT_CODE = "EDIT";
    public static final String DEL_CODE = "DEL";
    public static final String APP_CODE = "APP";
    public static final String UNAPP_CODE = "UNAPP";
    public static final String UPLOAD_CODE = "UPLOAD";
    public static final String DOWNLOAD_CODE = "DOWNLOAD";
    public static final String IMPORT_CODE = "IMPORT";
    public static final String EXPORT_CODE = "EXPORT";
    public static final String PRINT_CODE = "PRINT";
    public static final String ENABLE_CODE = "ENABLE";

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
