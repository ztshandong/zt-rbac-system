package com.zhangzhuorui.framework.rbacsystem.entity;

import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacBasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author :  张涛 zhangtao
 * @createDate :  2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version :  1.0
 */

/**
 * 特殊隐藏的字段。这个表中记录的字段默认所有人都可见，只有指定的用户（或者角色，部门等）不可查看
 */
@ApiModel(value = "ZtSpecHide")
public class ZtSpecHide extends ZtRbacBasicEntity<Long> {
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String vxeTableName;

    /**
     * 列字段名
     */
    @ApiModelProperty(value = "列字段名")
    private String vxeFieldName;

    /**
     * ZtRoleStatusEnum权限类型
     */
    @ApiModelProperty(value = "ZtRoleStatusEnum权限类型")
    private ZtRoleStatusEnum specType;

    /**
     * 编号（用户编号，部门编号，职位编号，角色编号，取决于specType）
     */
    @ApiModelProperty(value = "编号（用户编号，部门编号，职位编号，角色编号，取决于specType）")
    private String specCode;

    public String getVxeTableName() {
        return vxeTableName;
    }

    public void setVxeTableName(String vxeTableName) {
        this.vxeTableName = vxeTableName;
    }

    public String getVxeFieldName() {
        return vxeFieldName;
    }

    public void setVxeFieldName(String vxeFieldName) {
        this.vxeFieldName = vxeFieldName;
    }

    public ZtRoleStatusEnum getSpecType() {
        return specType;
    }

    public void setSpecType(ZtRoleStatusEnum specType) {
        this.specType = specType;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", vxeTableName=").append(vxeTableName);
        sb.append(", vxeFieldName=").append(vxeFieldName);
        sb.append(", specType=").append(specType);
        sb.append(", specCode=").append(specCode);
        sb.append(", gmtCreate=").append(this.getGmtCreate());
        sb.append(", gmtUpdate=").append(this.getGmtUpdate());
        sb.append(", createdBy=").append(this.getCreatedBy());
        sb.append(", updatedBy=").append(this.getUpdatedBy());
        sb.append(", remark=").append(this.getRemark());
        sb.append(", createdByName=").append(this.getCreatedByName());
        sb.append(", updatedByName=").append(this.getUpdatedByName());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ZtSpecHide other = (ZtSpecHide) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getVxeTableName() == null ? other.getVxeTableName() == null : this.getVxeTableName().equals(other.getVxeTableName()))
                && (this.getVxeFieldName() == null ? other.getVxeFieldName() == null : this.getVxeFieldName().equals(other.getVxeFieldName()))
                && (this.getSpecType() == null ? other.getSpecType() == null : this.getSpecType().equals(other.getSpecType()))
                && (this.getSpecCode() == null ? other.getSpecCode() == null : this.getSpecCode().equals(other.getSpecCode()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtUpdate() == null ? other.getGmtUpdate() == null : this.getGmtUpdate().equals(other.getGmtUpdate()))
                && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
                && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getCreatedByName() == null ? other.getCreatedByName() == null : this.getCreatedByName().equals(other.getCreatedByName()))
                && (this.getUpdatedByName() == null ? other.getUpdatedByName() == null : this.getUpdatedByName().equals(other.getUpdatedByName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVxeTableName() == null) ? 0 : getVxeTableName().hashCode());
        result = prime * result + ((getVxeFieldName() == null) ? 0 : getVxeFieldName().hashCode());
        result = prime * result + ((getSpecType() == null) ? 0 : getSpecType().hashCode());
        result = prime * result + ((getSpecCode() == null) ? 0 : getSpecCode().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtUpdate() == null) ? 0 : getGmtUpdate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatedByName() == null) ? 0 : getCreatedByName().hashCode());
        result = prime * result + ((getUpdatedByName() == null) ? 0 : getUpdatedByName().hashCode());
        return result;
    }
}