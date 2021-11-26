package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserPostInfo;
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
@Api(tags = "用户职位关联", value = "用户职位关联", description = "ZtUserPostInfoController")
@RestController
@RequestMapping(value = "/ZtUserPostInfo")
public class ZtUserPostInfoController extends ZtRbacSimpleBaseController<ZtUserPostInfo> {

}

