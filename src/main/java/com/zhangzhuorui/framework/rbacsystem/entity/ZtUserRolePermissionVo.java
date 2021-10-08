package com.zhangzhuorui.framework.rbacsystem.entity;

import java.util.List;

/**
 * @author :  zhangtao
 * @version :  1.0
 * @createDate : 2020-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public class ZtUserRolePermissionVo {

    private ZtUserInfo user;

    private List<String> roles;

    private List<String> permissions;

    public ZtUserInfo getUser() {
        return user;
    }

    public void setUser(ZtUserInfo user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
