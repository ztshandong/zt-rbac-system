package com.zhangzhuorui.framework.rbacsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserRolePermissionVo;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
@Slf4j
public class ZtIndexController {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    @Autowired
    IZtUserInfoService iZtUserInfoService;

    @Value("${wx:appId}")
    String appId;

    @Value("${wx:secret}")
    String secret;

    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ZtResBeanEx<String> login(@RequestBody ZtUserInfo ztUserInfo) {
        List<ZtUserInfo> ztUserInfos = iZtUserInfoService.ztSimpleGetList(ztUserInfo);
        ZtUserInfo ztUserInfo1 = ztUserInfos.get(0);
        String token = ztJwtTokenUtil.generateToken(ztUserInfo1);
        ztCacheUtil.refreshCacheByCurUserId(ztUserInfo1.getId());
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
        userInfo = iZtUserInfoService.getFullUserInfoFromToken(userInfo);
        iZtRoleInfoService.getCurUserAdminFlag(userInfo);
        ZtUserRolePermissionVo ztUserRolePermissionVo = new ZtUserRolePermissionVo();
        List<String> curUserPermission = iZtRoleInfoService.getCurUserPermission(userInfo);
        List<String> curUserAllRoleCodes = iZtRoleInfoService.getCurUserAllRoleCodes(userInfo);
        ztUserRolePermissionVo.setPermissions(curUserPermission);
        ztUserRolePermissionVo.setRoles(curUserAllRoleCodes);
        ztUserRolePermissionVo.setUser(userInfo);
        return ZtResBeanEx.ok(ztUserRolePermissionVo);
    }

    @ApiOperation(value = "获取openid")
    @GetMapping("getOpenIdInXiaoChengXu")
    @ResponseBody
    public Object getOpenIdInXiaoChengXu(@RequestParam String code) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        System.out.println("url==========" + url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String strEntity = null;
        try {
            strEntity = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("get openid err");
        }
        JSONObject jsonObject = JSON.parseObject(strEntity);
        log.info("getOpenIdInXiaoChengXu:=====================:{}", jsonObject);
        try {
            response.close();
        } catch (Exception e) {
            log.error("close error", e);
        }
        return jsonObject;
    }
}

