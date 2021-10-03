package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@Api
@RestController
@RequestMapping(value = "/ZtIndex")
public class ZtIndexController {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @ResponseBody
    @RequestMapping(value = "getToken", method = RequestMethod.POST)
    public String getToken(@RequestBody ZtUserInfo ztUserInfo) {
        String token = ztJwtTokenUtil.generateToken(ztUserInfo);
        return token;
    }

}

