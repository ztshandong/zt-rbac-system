package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtSpecShow;
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
@ApiSort(value = 407)
@Api(tags = "特殊显示字段", value = "特殊显示字段", description = "ZtSpecShowController")
@RestController
@RequestMapping(value = "/ZtSpecShow")
@ZtPreAuthorize(hasAnyPermi = {ZtMenuCodeEnum.SPEC_SHOW_CODE})
public class ZtSpecShowController extends ZtRbacSimpleBaseController<ZtSpecShow> {

}

