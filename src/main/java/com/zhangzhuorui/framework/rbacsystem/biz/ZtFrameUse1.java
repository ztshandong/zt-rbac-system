package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.mybatis.enums.ZtFrameEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacBasicEntity;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例 常规用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
// @ApiModel(value="ZtTest1")
public class ZtFrameUse1 extends ZtRbacBasicEntity<Long> {
    /**
     * 字段1
     */
    // @ApiModelProperty(value = "字段1")
    private String udf1;

    /**
     * 字段2
     */
    // @ApiModelProperty(value = "字段2")
    private Long udf2;

    /**
     * 启用
     */
    // @ApiModelProperty(value = "启用")
    private Boolean enableFlag;

    /**
     * 是否已删除
     */
    // @ApiModelProperty(value = "删除")
    private Boolean delFlag;

    /**
     * 枚举字段
     */
    private ZtFrameEnum enum1;

    /**
     * 数据权限：部门编号
     */
    private String deptCode;

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public Long getUdf2() {
        return udf2;
    }

    public void setUdf2(Long udf2) {
        this.udf2 = udf2;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public ZtFrameEnum getEnum1() {
        return enum1;
    }

    public void setEnum1(ZtFrameEnum enum1) {
        this.enum1 = enum1;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}