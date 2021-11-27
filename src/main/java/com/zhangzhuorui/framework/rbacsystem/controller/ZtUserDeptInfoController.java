package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
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
@ApiSort(value = 301)
@Api(tags = "用户部门关联", value = "用户部门关联", description = "ZtUserDeptInfoController")
@RestController
@RequestMapping(value = "/ZtUserDeptInfo")
@ZtPreAuthorize(hasAnyPermi = {ZtMenuCodeEnum.USER_DEPT_CODE})
public class ZtUserDeptInfoController extends ZtRbacSimpleBaseController<ZtUserDeptInfo> {

}

