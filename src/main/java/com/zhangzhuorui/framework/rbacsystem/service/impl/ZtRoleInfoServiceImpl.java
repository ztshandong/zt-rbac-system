package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserDeptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统 服务实现类
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtRoleInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleInfo> implements IZtRoleInfoService {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtDeptInfoService iZtDeptInfoService;

    @Autowired
    IZtUserDeptInfoService iZtUserDeptInfoService;

    @Override
    public String getTableName() {
        return "zt_role_info";
    }

    /**
     * 获取用户所有角色
     * 超级管理员有所有权限
     * <p>
     * 1.getCurUserDeptCodes
     * 2.查询部门角色，排除特定角色
     * 3.getCurUserPostInfos
     * 4.查询职位角色，排除特定角色
     * 5.查询用户角色，排除特定角色
     * 6.汇总部门角色职位角色与用户角色，并排除特定角色
     * 7.查询子角色，排除特定角色，得到最终角色
     * 包含菜单角色与数据角色
     */
    public void getCurUserAllRoles() {

    }

    /**
     * 菜单权限：向前端返回的路由
     * 1.getCurUserAllRoles
     * 2.过滤菜单角色与综合角色
     * 3.查询组件角色关联表，排除特定组件
     * 4.拼装路由
     */
    public void getCurUserMenuRoles() {

    }

    /**
     * 数据权限：dataScopeDeptFlag使用
     * 1.getCurUserAllRoles
     * 2.过滤数据角色与综合角色
     * 3.ZtQueryTypeEnum是AND的DEPT_CUSTOM与getCurUserDeptCodes取交集
     * 4.添加ZtQueryTypeEnum是OR的DEPT_CUSTOM
     */
    public void getCurUserDataRoleDepts() {

    }

    /**
     * 1.getCurUserAllRoles
     * 2.过滤数据角色与综合角色
     * 3.ZtQueryTypeEnum是AND的POST_CUSTOM与getCurUserPostInfos取交集
     * 4.添加ZtQueryTypeEnum是OR的POST_CUSTOM
     */
    public void getCurUserDataRolePosts() {

    }

    /**
     * 数据权限：dataScopeUserFlag使用
     * getCurUserDataRolePosts获得对应的用户，userLists
     * 1.getCurUserAllRoles
     * 2.过滤数据角色与综合角色
     * 3.ZtQueryTypeEnum是AND的USER_CUSTOM与userLists取交集
     * 4.添加ZtQueryTypeEnum是OR的USER_CUSTOM
     */
    public void getCurUserDataRoleUsers() {

    }

}

