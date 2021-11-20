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
public class ZtUserInfo extends ZtRbacBasicEntity<String> {
    /**
     * 用户编号（唯一，不可修改，作为其他表外键，与id相同）
     */
    @ApiModelProperty(value = "用户编号（唯一，不可修改，作为其他表外键，与id相同）")
    private String userCode;

    /**
     * 用户名（唯一，暂定不可修改）
     */
    @ApiModelProperty(value = "用户名（唯一，暂定不可修改）")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户性别：0 未知 1 男性 2 女性
     */
    @ApiModelProperty(value = "用户性别：0 未知 1 男性 2 女性")
    private Integer gender;

    /**
     * 用户状态：0 正常，1 禁用，2 审核中，3 审核拒绝，4 已注销
     */
    @ApiModelProperty(value = "用户状态：0 正常，1 禁用，2 审核中，3 审核拒绝，4 已注销")
    private Integer status;

    /**
     * 手机号（唯一，可修改）
     */
    @ApiModelProperty(value = "手机号（唯一，可修改）")
    private String mobile;

    /**
     * 手机号验证状态：0 未验证 1 已验证，未验证用户不可登录
     */
    @ApiModelProperty(value = "手机号验证状态：0 未验证 1 已验证，未验证用户不可登录")
    private Integer mobile_confirmed;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    /**
     * 邮箱验证状态：0 未验证 1 已验证，未验证用户不可登录
     */
    @ApiModelProperty(value = "邮箱验证状态：0 未验证 1 已验证，未验证用户不可登录")
    private Integer email_confirmed;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String wx_unionid;

    /**
     * 微信各个平台openid
     */
    @ApiModelProperty(value = "微信各个平台openid")
    private String wx_openid;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMobile_confirmed() {
        return mobile_confirmed;
    }

    public void setMobile_confirmed(Integer mobile_confirmed) {
        this.mobile_confirmed = mobile_confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmail_confirmed() {
        return email_confirmed;
    }

    public void setEmail_confirmed(Integer email_confirmed) {
        this.email_confirmed = email_confirmed;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWx_unionid() {
        return wx_unionid;
    }

    public void setWx_unionid(String wx_unionid) {
        this.wx_unionid = wx_unionid;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
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
        sb.append(", userName=").append(username);
        sb.append(", userNickName=").append(nickname);
        sb.append(", userPwd=").append(password);
        sb.append(", userMobile=").append(mobile);
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
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
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
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
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
