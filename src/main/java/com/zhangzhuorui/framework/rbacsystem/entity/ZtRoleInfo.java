package com.zhangzhuorui.framework.rbacsystem.entity;

import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtDataScopeTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleTypeEnum;
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
 * 角色信息表
 */
@ApiModel(value = "ZtRoleInfo")
public class ZtRoleInfo extends ZtRbacBasicEntity<Long> {
    /**
     * 角色编号（唯一，不可修改）
     */
    @ApiModelProperty(value = "角色编号（唯一，不可修改）")
    private String thisCode;

    /**
     * 上级角色编号
     */
    @ApiModelProperty(value = "上级角色编号")
    private String parentCode;

    /**
     * /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String thisName;

    /**
     * 角色类型（菜单角色-读取zt_role_component_info表，数据角色-读取data_scope_type字段，综合角色-菜单角色与数据角色）
     */
    @ApiModelProperty(value = "角色类型（菜单角色-读取zt_role_component_info表，数据角色-读取data_scope_type字段，综合角色-菜单角色与数据角色）")
    private ZtRoleTypeEnum roleType;

    /**
     * 角色状态（禁用，启用）
     */
    @ApiModelProperty(value = "角色状态（禁用，启用）")
    private Boolean enableFlag;

    /**
     * 是否是管理员角色
     */
    @ApiModelProperty(value = "是否是管理员角色")
    private Boolean adminFlag;

    /**
     * 菜单权限：是否包含子角色。默认包含
     */
    @ApiModelProperty(value = "菜单权限：是否包含子角色。默认包含")
    private Boolean withChildrenFlag;

    /**
     * 数据权限类型
     * 一个用户可以有多个数据角色，多个数据角色会有优先级。整体优先级SELF和ALL优先判断
     * SELF(-2, "只能查看自己的数据。如果有这个，并且是AND，就会忽略其他条件，优先级最高"),
     * ALL(-1, "可以查看所有人的数据。没有AND的SELF的前提下，如果有这个，并且是OR，会忽略其他条件，优先级次高"),
     * <p>
     * 下面的角色先汇总计算AND，然后计算OR
     * DEPT_ALL(1, "用户所属部门及子部门所有数据"),
     * DEPT_CUSTOM(2, "自定义部门数据，不级联查询子部门。用户不属于该部门，但又要看到数据"),
     * POST_ALL(3, "用户职位及下级职位所有数据。职位最终会转换成人员账号查询"),
     * POST_CUSTOM(4, "自定义职位数据，不级联查询下级职位。用户需要查看上级职位数据"),
     * USER_CUSTOM(5, "自定义查看指定用户的数据"),
     */
    @ApiModelProperty(value = "数据权限类型")
    private ZtDataScopeTypeEnum dataScopeType;

    /**
     * dataScopeType字段专用
     * 当前数据权限角色与其他数据权限角色汇总时，取交集还是并集
     * AND取并集，从严处理。（例如，只能看到某个部门的某个职位）
     * OR取交集，汇总添加。（例如，即要看到指定部门，又要看到指定职位，还要看到指定人员）
     */
    @ApiModelProperty(value = "dataScopeType字段专用")
    private ZtQueryTypeEnum dataScopeOptType;

    /**
     * 数据权限类型为自定义时使用。DEPT_CUSTOM存部门编号，POST_CUSTOM存职位编号，USER_CUSTOM存用户编号
     */
    @ApiModelProperty(value = "数据权限类型为自定义时使用。DEPT_CUSTOM存部门编号，POST_CUSTOM存职位编号，USER_CUSTOM存用户编号")
    private List<String> roleCustom;

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

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public ZtRoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(ZtRoleTypeEnum roleType) {
        this.roleType = roleType;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Boolean getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public Boolean getWithChildrenFlag() {
        return withChildrenFlag;
    }

    public void setWithChildrenFlag(Boolean withChildrenFlag) {
        this.withChildrenFlag = withChildrenFlag;
    }

    public ZtDataScopeTypeEnum getDataScopeType() {
        return dataScopeType;
    }

    public void setDataScopeType(ZtDataScopeTypeEnum dataScopeType) {
        this.dataScopeType = dataScopeType;
    }

    public ZtQueryTypeEnum getDataScopeOptType() {
        return dataScopeOptType;
    }

    public void setDataScopeOptType(ZtQueryTypeEnum dataScopeOptType) {
        this.dataScopeOptType = dataScopeOptType;
    }

    public List<String> getRoleCustom() {
        return roleCustom;
    }

    public void setRoleCustom(List<String> roleCustom) {
        this.roleCustom = roleCustom;
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
        sb.append(", thisName=").append(thisName);
        sb.append(", roleType=").append(roleType);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", adminFlag=").append(adminFlag);
        sb.append(", withChildrenFlag=").append(withChildrenFlag);
        sb.append(", dataScopeType=").append(dataScopeType);
        sb.append(", dataScopeOptType=").append(dataScopeOptType);
        sb.append(", deptCustom=").append(roleCustom);
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
        ZtRoleInfo other = (ZtRoleInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getThisCode() == null ? other.getThisCode() == null : this.getThisCode().equals(other.getThisCode()))
                && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
                && (this.getThisName() == null ? other.getThisName() == null : this.getThisName().equals(other.getThisName()))
                && (this.getRoleType() == null ? other.getRoleType() == null : this.getRoleType().equals(other.getRoleType()))
                && (this.getEnableFlag() == null ? other.getEnableFlag() == null : this.getEnableFlag().equals(other.getEnableFlag()))
                && (this.getAdminFlag() == null ? other.getAdminFlag() == null : this.getAdminFlag().equals(other.getAdminFlag()))
                && (this.getWithChildrenFlag() == null ? other.getWithChildrenFlag() == null : this.getWithChildrenFlag().equals(other.getWithChildrenFlag()))
                && (this.getDataScopeType() == null ? other.getDataScopeType() == null : this.getDataScopeType().equals(other.getDataScopeType()))
                && (this.getDataScopeOptType() == null ? other.getDataScopeOptType() == null : this.getDataScopeOptType().equals(other.getDataScopeOptType()))
                && (this.getRoleCustom() == null ? other.getRoleCustom() == null : this.getRoleCustom().equals(other.getRoleCustom()))
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
        result = prime * result + ((getThisName() == null) ? 0 : getThisName().hashCode());
        result = prime * result + ((getRoleType() == null) ? 0 : getRoleType().hashCode());
        result = prime * result + ((getEnableFlag() == null) ? 0 : getEnableFlag().hashCode());
        result = prime * result + ((getAdminFlag() == null) ? 0 : getAdminFlag().hashCode());
        result = prime * result + ((getWithChildrenFlag() == null) ? 0 : getWithChildrenFlag().hashCode());
        result = prime * result + ((getDataScopeType() == null) ? 0 : getDataScopeType().hashCode());
        result = prime * result + ((getDataScopeOptType() == null) ? 0 : getDataScopeOptType().hashCode());
        result = prime * result + ((getRoleCustom() == null) ? 0 : getRoleCustom().hashCode());
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