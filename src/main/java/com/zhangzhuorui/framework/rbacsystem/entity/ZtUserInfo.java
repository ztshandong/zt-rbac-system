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
 * 用户信息表
 */
@ApiModel(value = "ZtUserInfo")
public class ZtUserInfo extends ZtRbacBasicEntity<Long> {
    /**
     * 用户编号（唯一，不可修改，作为其他表外键）
     */
    @ApiModelProperty(value = "用户编号（唯一，不可修改，作为其他表外键）")
    private String userCode;

    /**
     * 用户名（唯一，暂定不可修改）
     */
    @ApiModelProperty(value = "用户名（唯一，暂定不可修改）")
    private String userName;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String userNickName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String userPwd;

    /**
     * 手机号（唯一，可修改）
     */
    @ApiModelProperty(value = "手机号（唯一，可修改）")
    private String userMobile;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 是否是管理员
     */
    @ApiModelProperty(value = "是否是管理员")
    private Boolean adminFlag;

    /**
     * 是否被锁定
     */
    @ApiModelProperty(value = "是否被锁定")
    private Boolean blockFlag;

    /**
     * 默认所属部门
     */
    @ApiModelProperty(value = "默认所属部门")
    private String defaultDeptCode;

    /**
     * 默认职位
     */
    @ApiModelProperty(value = "默认职位")
    private String defaultPostCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public Boolean getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(Boolean blockFlag) {
        this.blockFlag = blockFlag;
    }

    public String getDefaultDeptCode() {
        return defaultDeptCode;
    }

    public void setDefaultDeptCode(String defaultDeptCode) {
        this.defaultDeptCode = defaultDeptCode;
    }

    public String getDefaultPostCode() {
        return defaultPostCode;
    }

    public void setDefaultPostCode(String defaultPostCode) {
        this.defaultPostCode = defaultPostCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", userCode=").append(userCode);
        sb.append(", userName=").append(userName);
        sb.append(", userNickName=").append(userNickName);
        sb.append(", userPwd=").append(userPwd);
        sb.append(", userMobile=").append(userMobile);
        sb.append(", avatar=").append(avatar);
        sb.append(", adminFlag=").append(adminFlag);
        sb.append(", blockFlag=").append(blockFlag);
        sb.append(", defaultDeptCode=").append(defaultDeptCode);
        sb.append(", defaultPostCode=").append(defaultPostCode);
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
        ZtUserInfo other = (ZtUserInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
                && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
                && (this.getUserNickName() == null ? other.getUserNickName() == null : this.getUserNickName().equals(other.getUserNickName()))
                && (this.getUserPwd() == null ? other.getUserPwd() == null : this.getUserPwd().equals(other.getUserPwd()))
                && (this.getUserMobile() == null ? other.getUserMobile() == null : this.getUserMobile().equals(other.getUserMobile()))
                && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
                && (this.getAdminFlag() == null ? other.getAdminFlag() == null : this.getAdminFlag().equals(other.getAdminFlag()))
                && (this.getBlockFlag() == null ? other.getBlockFlag() == null : this.getBlockFlag().equals(other.getBlockFlag()))
                && (this.getDefaultDeptCode() == null ? other.getDefaultDeptCode() == null : this.getDefaultDeptCode().equals(other.getDefaultDeptCode()))
                && (this.getDefaultPostCode() == null ? other.getDefaultPostCode() == null : this.getDefaultPostCode().equals(other.getDefaultPostCode()))
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
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserNickName() == null) ? 0 : getUserNickName().hashCode());
        result = prime * result + ((getUserPwd() == null) ? 0 : getUserPwd().hashCode());
        result = prime * result + ((getUserMobile() == null) ? 0 : getUserMobile().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getAdminFlag() == null) ? 0 : getAdminFlag().hashCode());
        result = prime * result + ((getBlockFlag() == null) ? 0 : getBlockFlag().hashCode());
        result = prime * result + ((getDefaultDeptCode() == null) ? 0 : getDefaultDeptCode().hashCode());
        result = prime * result + ((getDefaultPostCode() == null) ? 0 : getDefaultPostCode().hashCode());
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