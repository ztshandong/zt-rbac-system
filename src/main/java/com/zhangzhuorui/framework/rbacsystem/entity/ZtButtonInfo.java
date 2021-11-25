package com.zhangzhuorui.framework.rbacsystem.entity;

import com.zhangzhuorui.framework.rbacsystem.enums.ZtButtonCodeEnum;
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
 * 按钮信息表-code就是权限
 */
@ApiModel(value = "按钮信息表")
public class ZtButtonInfo extends ZtRbacBasicEntity<Long> {
    /**
     * 按钮编号（唯一，不可修改）
     */
    @ApiModelProperty(value = "按钮编号（唯一，不可修改）")
    private ZtButtonCodeEnum thisCode;

    /**
     * 按钮名称
     */
    @ApiModelProperty(value = "按钮名称")
    private String thisName;

    /**
     * 按钮状态（true启用，false禁用）
     */
    @ApiModelProperty(value = "按钮状态（true启用，false禁用）")
    private Boolean enableFlag;

    /**
     * 图标URL
     */
    @ApiModelProperty(value = "图标URL")
    private String iconUrl;

    public ZtButtonCodeEnum getThisCode() {
        return thisCode;
    }

    public void setThisCode(ZtButtonCodeEnum thisCode) {
        this.thisCode = thisCode;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", thisCode=").append(thisCode);
        sb.append(", thisName=").append(thisName);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", iconUrl=").append(iconUrl);
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
        ZtButtonInfo other = (ZtButtonInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getThisCode() == null ? other.getThisCode() == null : this.getThisCode().equals(other.getThisCode()))
                && (this.getThisName() == null ? other.getThisName() == null : this.getThisName().equals(other.getThisName()))
                && (this.getEnableFlag() == null ? other.getEnableFlag() == null : this.getEnableFlag().equals(other.getEnableFlag()))
                && (this.getIconUrl() == null ? other.getIconUrl() == null : this.getIconUrl().equals(other.getIconUrl()))
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
        result = prime * result + ((getThisCode() == null) ? 0 : getThisCode().hashCode());
        result = prime * result + ((getThisName() == null) ? 0 : getThisName().hashCode());
        result = prime * result + ((getEnableFlag() == null) ? 0 : getEnableFlag().hashCode());
        result = prime * result + ((getIconUrl() == null) ? 0 : getIconUrl().hashCode());
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
