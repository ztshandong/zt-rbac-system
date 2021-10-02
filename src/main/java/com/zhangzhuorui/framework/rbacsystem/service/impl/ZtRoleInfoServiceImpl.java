package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
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

    @Override
    public String getTableName() {
        return "zt_role_info";
    }

    /**
     * 超级管理员有所有权限
     * <p>
     * 1.查询部门，排除特定部门
     * 2.查询子部门，排除特定部门
     * 3.查询部门角色，排除特定角色
     * 4.查询职位，排除特定职位
     * 5.查询子职位，排除特定职位
     * 6.查询职位角色，排除特定角色
     * 7.查询用户角色，排除特定角色
     * 8.汇总部门角色职位角色与用户角色，并排除特定角色
     * 9.查询子角色，排除特定角色，得到最终角色
     */
    public void getRole() {

    }

    /**
     * getRole之后
     * 1.查询组件，排除特定组件，就是最终的菜单权限
     * 2.拼装路由，缓存
     */
    public void getMenuRole() {

    }
}

