package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
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
@Api(tags = "用户", value = "用户", description = "ZtUserInfoController")
@RestController
@RequestMapping(value = "/ZtUserInfo")
@ZtPreAuthorize(hasAnyPermi = {ZtMenuCodeEnum.USER_MANAGE_CODE})
public class ZtUserInfoController extends ZtRbacSimpleBaseController<ZtUserInfo> {

    // @Override
    // @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.QUERY_CODE}, denyPermi = {"rolemanage:" + ZtButtonCodeEnum.QUERY_CODE})
    // protected ZtParamEntity<ZtUserInfo> beforeSelect(ZtParamEntity<ZtUserInfo> ztParamEntity) {
    //     return super.beforeSelect(ztParamEntity);
    // }
}

