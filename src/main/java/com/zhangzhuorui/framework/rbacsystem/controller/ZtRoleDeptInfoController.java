package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Api(tags = "角色部门关联", value = "角色部门关联", description = "ZtRoleDeptInfoController")
@RestController
@RequestMapping(value = "/ZtRoleDeptInfo")
public class ZtRoleDeptInfoController extends ZtRbacSimpleBaseController<ZtRoleDeptInfo> {

}

