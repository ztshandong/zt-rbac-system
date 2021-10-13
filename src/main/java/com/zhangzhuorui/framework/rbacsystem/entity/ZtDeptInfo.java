package com.zhangzhuorui.framework.rbacsystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacBasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
 * 部门信息表
 */
@ApiModel(value = "ZtDeptInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZtDeptInfo extends ZtRbacBasicEntity<Long> {
    /**
     * 部门编号（唯一，不可修改）
     */
    @ApiModelProperty(value = "部门编号（唯一，不可修改）")
    private String thisCode;

    public String getValue() {
        return thisCode;
    }

    public void setValue(String code) {
        this.thisCode = code;
    }

    /**
     * 上级部门编号（同公司）
     */
    @ApiModelProperty(value = "上级部门编号（同公司）")
    private String parentCode;

    /**
     * 上级部门编号（集团化。例如集团信息部可以管控所有子公司信息部）
     */
    @ApiModelProperty(value = "上级部门编号（集团化。例如集团信息部可以管控所有子公司信息部）")
    private String rootCode;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String thisName;

    /**
     * 部门状态（true启用，false禁用）
     */
    @ApiModelProperty(value = "部门状态（true启用，false禁用）")
    private Boolean enableFlag;

    /**
     * 部门层级
     */
    @ApiModelProperty(value = "部门层级")
    private Integer deptLevel;

    /**
     * 部门负责人编号
     */
    @ApiModelProperty(value = "部门负责人编号")
    private String deptLeaderCode;

    //---------------分割线以下的字段数据库不存在-----------------

    /**
     * 子组件
     */
    @ApiModelProperty(value = "子组件")
    private List<ZtDeptInfo> children;

    public String getLabel() {
        return thisName;
    }

    /**
     * 上级部门名称
     */
    @ApiModelProperty(value = "上级部门名称")
    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getThisCode() {
        return thisCode;
    }

    public void setThisCode(String thisCode) {
        this.thisCode = thisCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
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

    public Integer getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Integer deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getDeptLeaderCode() {
        return deptLeaderCode;
    }

    public void setDeptLeaderCode(String deptLeaderCode) {
        this.deptLeaderCode = deptLeaderCode;
    }

    public List<ZtDeptInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ZtDeptInfo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", thisCode=").append(thisCode);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", rootCode=").append(rootCode);
        sb.append(", thisName=").append(thisName);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", deptLevel=").append(deptLevel);
        sb.append(", deptLeaderCode=").append(deptLeaderCode);
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
        ZtDeptInfo other = (ZtDeptInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getThisCode() == null ? other.getThisCode() == null : this.getThisCode().equals(other.getThisCode()))
                && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
                && (this.getRootCode() == null ? other.getRootCode() == null : this.getRootCode().equals(other.getRootCode()))
                && (this.getThisName() == null ? other.getThisName() == null : this.getThisName().equals(other.getThisName()))
                && (this.getEnableFlag() == null ? other.getEnableFlag() == null : this.getEnableFlag().equals(other.getEnableFlag()))
                && (this.getDeptLevel() == null ? other.getDeptLevel() == null : this.getDeptLevel().equals(other.getDeptLevel()))
                && (this.getDeptLeaderCode() == null ? other.getDeptLeaderCode() == null : this.getDeptLeaderCode().equals(other.getDeptLeaderCode()))
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
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getRootCode() == null) ? 0 : getRootCode().hashCode());
        result = prime * result + ((getThisName() == null) ? 0 : getThisName().hashCode());
        result = prime * result + ((getEnableFlag() == null) ? 0 : getEnableFlag().hashCode());
        result = prime * result + ((getDeptLevel() == null) ? 0 : getDeptLevel().hashCode());
        result = prime * result + ((getDeptLeaderCode() == null) ? 0 : getDeptLeaderCode().hashCode());
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