package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserRolePermissionVo;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    @Autowired
    IZtUserInfoService iZtUserInfoService;

    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ZtResBeanEx<String> login(@RequestBody ZtUserInfo ztUserInfo) {
        List<ZtUserInfo> ztUserInfos = iZtUserInfoService.ztSimpleGetList(ztUserInfo);
        String token = ztJwtTokenUtil.generateToken(ztUserInfos.get(0));
        // iZtUserInfoService.getFullUserInfoFromToken(ztUserInfo);
        ZtResBeanEx<String> ok = ZtResBeanEx.ok();
        ok.setData(token);
        return ok;
    }

    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout() {
        ZtUserInfo userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        ztCacheUtil.refreshCacheByCurUserId(userInfo.getId());
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "refreshAllCache", method = RequestMethod.POST)
    public String refreshAllCache() {
        ztCacheUtil.refreshAllCache();
        return "refreshAllCache";
    }

    @ResponseBody
    @RequestMapping(value = "getCurUserRouteAfterLogin", method = RequestMethod.POST)
    public ZtResBeanEx<List<ZtComponentInfo>> getCurUserRouteAfterLogin() {
        ZtUserInfo userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        List<ZtComponentInfo> curUserRouteAfterLogin = iZtRoleInfoService.getCurUserRouteAfterLogin(userInfo);
        return ZtResBeanEx.ok(curUserRouteAfterLogin);
    }

    @ResponseBody
    @RequestMapping(value = "getUserInfoAfterLogin", method = RequestMethod.POST)
    public ZtResBeanEx<ZtUserRolePermissionVo> getUserInfoAfterLogin() {
        ZtUserInfo userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        iZtUserInfoService.getFullUserInfoFromToken(userInfo);
        iZtRoleInfoService.getCurUserAdminFlag(userInfo);
        ZtUserRolePermissionVo ztUserRolePermissionVo = new ZtUserRolePermissionVo();
        List<String> curUserPermission = iZtRoleInfoService.getCurUserPermission(userInfo);
        List<String> curUserAllRoleCodes = iZtRoleInfoService.getCurUserAllRoleCodes(userInfo);
        ZtUserInfo fullUserInfoFromCache = ztCacheUtil.getFullUserInfoFromCache(userInfo);
        ztUserRolePermissionVo.setPermissions(curUserPermission);
        ztUserRolePermissionVo.setRoles(curUserAllRoleCodes);
        ztUserRolePermissionVo.setUser(fullUserInfoFromCache);
        return ZtResBeanEx.ok(ztUserRolePermissionVo);
    }
}

