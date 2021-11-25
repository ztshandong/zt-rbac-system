package com.zhangzhuorui.framework.rbacsystem.entity;

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
 * 用户部门关联表
 */
@ApiModel(value = "用户部门关联表")
public class ZtUserDeptInfo extends ZtRbacBasicEntity<Long> {
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private String userCode;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    private String deptCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", userCode=").append(userCode);
        sb.append(", deptCode=").append(deptCode);
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
        ZtUserDeptInfo other = (ZtUserDeptInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
                && (this.getDeptCode() == null ? other.getDeptCode() == null : this.getDeptCode().equals(other.getDeptCode()))
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
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getDeptCode() == null) ? 0 : getDeptCode().hashCode());
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
