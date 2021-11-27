package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtButtonInfo;
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
@ApiSort(value = 600)
@Api(tags = "按钮", value = "按钮", description = "ZtButtonInfoController")
@RestController
@RequestMapping(value = "/ZtButtonInfo")
@ZtPreAuthorize(hasAnyPermi = {ZtMenuCodeEnum.BUTTON_MANAGE_CODE})
public class ZtButtonInfoController extends ZtRbacSimpleBaseController<ZtButtonInfo> {

    // @ApiResponses(value = {
    //         @ApiResponse(code = 200, message = "返回的消息体", response = ZtPage.class)
    // })
    // @Override
    // public ZtResBeanEx<ZtPage<ZtButtonInfo>> selectSimple(@RequestBody ZtButtonInfo entity) throws Exception {
    //     return super.selectSimple(entity);
    // }
    //
    // @ApiResponses(value = {
    //         @ApiResponse(code = 200, message = "返回的消息体", response = ZtButtonInfo.class)
    // })
    // @Override
    // public ZtResBeanEx<ZtButtonInfo> selectIdSimple(@RequestBody ZtButtonInfo entity) throws Exception {
    //     return super.selectIdSimple(entity);
    // }
}

