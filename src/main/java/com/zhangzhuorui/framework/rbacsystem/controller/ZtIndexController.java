package com.zhangzhuorui.framework.rbacsystem.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.core.ZtResBeanExConfig;
import com.zhangzhuorui.framework.core.ZtStrUtils;
import com.zhangzhuorui.framework.core.ZtUtils;
import com.zhangzhuorui.framework.rbacsystem.config.PasswordSecretConfig;
import com.zhangzhuorui.framework.rbacsystem.config.PasswordSecretEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserRolePermissionVo;
import com.zhangzhuorui.framework.rbacsystem.exception.ZtPreAuthorizeException;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ?????????????????????????????????RBAC??????????????????
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@ApiSort(value = 1)
@Api(tags = "???????????????", value = "???????????????", description = "ZtIndexController")
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

    @Value("${wx.miniAppId}")
    String miniAppId;

    @Value("${wx.miniSecret}")
    String miniSecret;

    @Value("${unicloud.domain}")
    String cloudFunctionDomain;

    @Value("${unicloud.path}")
    String cloudFunctionPath;

    @Value("${unicloud.appid}")
    String cloudFunctionAppId;

    @Autowired
    PasswordSecretConfig passwordSecretConfig;

    @Autowired
    ZtResBeanExConfig ztResBeanExConfig;

    @PostConstruct
    void init() {
        List<PasswordSecretEntity> list = passwordSecretConfig.getList();
        if (!ZtUtils.isEmptyList(list)) {
            list.sort((o1, o2) -> o2.getVersion().compareTo(o1.getVersion()));
        }
    }

    @ApiOperation(value = "????????????", notes = "??????:\n{\n" +
            "\t\"username\": \"admin\"\n" +
            "\t\"password\": \"123456\",\n" +
            "}")
    @ApiOperationSupport(order = 100)
    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ZtResBeanEx login(@RequestBody ZtUserInfo ztUserInfo, HttpServletRequest request) {
        if (StringUtils.isEmpty(ztUserInfo.getUsername()) || StringUtils.isEmpty(ztUserInfo.getPassword())) {
            throw new ZtPreAuthorizeException("????????????????????????");
        }
        ZtUserInfo ztUserInfo1 = iZtUserInfoService.login(ztUserInfo);
        if (null == ztUserInfo1) {
            throw new ZtPreAuthorizeException("????????????????????????");
        }
        //TODO ??????????????????????????????
        String token = ztJwtTokenUtil.generateToken(ztUserInfo1);
        request.setAttribute(ztJwtTokenUtil.getTokenHeader(), token);
        ztCacheUtil.refreshCacheByCurUserId(ztUserInfo1.getId());
        ZtResBeanEx ok = ZtResBeanEx.ok();
        ok.setData(token);
        ok.getResult().put("userinfo", ztUserInfo1);
        ZtResBeanEx<ZtUserRolePermissionVo> userInfoAfterLogin = getUserInfoAfterLogin(ztUserInfo1);
        ZtResBeanEx<List<ZtComponentInfo>> curUserRouteAfterLogin = getCurUserRouteAfterLogin(ztUserInfo1);
        ok.getResult().put("route", curUserRouteAfterLogin.getData());
        ok.getResult().put("user", userInfoAfterLogin.getData());
        return ok;
    }

    @ApiOperation(value = "uni??????", notes = "??????:\n{\n" +
            "\t\"username\": \"admin\"\n" +
            "\t\"password\": \"123456\",\n" +
            "}")
    @ApiOperationSupport(order = 100)
    @PostMapping("uniLogin")
    @ResponseBody
    public ZtResBeanEx uniLogin(@RequestBody JSONObject obj) {
        obj.put("appid", cloudFunctionAppId);
        //{"appid":"ggg","action":"login","clientInfo":{"CLIENTIP":"xx.xx.xx.xx","OS":"ios","PLATFORM":"h5","DEVICEID":"aaa"},"params":{"scene":"login","username":"testuser","password":"12345678"},"uniIdToken":"xxx"}
        log.info(JSON.toJSONString(obj));
        String url = cloudFunctionDomain + cloudFunctionPath;
        String result2 = HttpRequest.post(url)
                .body(JSON.toJSONString(obj))
                .execute().body();
        System.out.println(result2);
        Object params = obj.get("params");
        JSONObject paramsJsonObject = JSONObject.parseObject(JSON.toJSONString(params));
        StringBuilder signOriStr = ZtStrUtils.getSignOriStr(paramsJsonObject);
        String action = obj.getString("action");
        //password12345678sceneloginusernametestuser
        System.out.println(signOriStr);
        // String username = obj.getString("username");
        // String password = obj.getString("password");
        // String url = cloudFunctionDomain + cloudFunctionPath + "?" + "action=login&appid=" + cloudFunctionAppId + "&username=" + username + "&password=" + password;
        // System.out.println(url);
        // String s = HttpUtil.get(url);
        // System.out.println(s);
        //{"code":0,"msg":"","token":"bbb","uid":"fff","username":"name","type":"login","userInfo":{"_id":"fff","username":"name","password":"aaa","role":["admin"],"register_date":1636175395497,"register_ip":"116.224.234.97","token":["bbb","bbb"],"last_login_date":1636199523185,"last_login_ip":"116.224.234.97","login_ip_limit":[]},"tokenExpired":1636206723185,"errCode":0,"errMsg":"","message":"","needCaptcha":false}
        JSONObject jsonObject = JSON.parseObject(result2);
        return ZtResBeanEx.ok(jsonObject);
    }

    @ApiOperation(value = "??????")
    @ApiOperationSupport(order = 200)
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ZtResBeanEx logout() {
        ZtUserInfo userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        log.info(userInfo.getUserCode() + " logout");
        ztCacheUtil.refreshCacheByCurUserId(userInfo.getId());
        return ZtResBeanEx.ok("ok");
    }

    @ResponseBody
    @RequestMapping(value = "refreshAllCache", method = RequestMethod.POST)
    public ZtResBeanEx refreshAllCache() {
        ztCacheUtil.refreshAllCache();
        return ZtResBeanEx.ok("refreshAllCache");
    }

    @ApiOperation(value = "???????????????????????????", notes = "????????????")
    @ApiOperationSupport(order = 101)
    @ResponseBody
    @RequestMapping(value = "getUserInfoAfterLogin", method = RequestMethod.POST)
    public ZtResBeanEx<ZtUserRolePermissionVo> getUserInfoAfterLogin(@RequestBody(required = false) ZtUserInfo userInfo) {
        if (userInfo == null) {
            userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        }
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

    @ApiOperation(value = "?????????????????????", notes = "????????????")
    @ApiOperationSupport(order = 102)
    @ResponseBody
    @RequestMapping(value = "getCurUserRouteAfterLogin", method = RequestMethod.POST)
    public ZtResBeanEx<List<ZtComponentInfo>> getCurUserRouteAfterLogin(@RequestBody(required = false) ZtUserInfo userInfo) {
        if (userInfo == null) {
            userInfo = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        }
        List<ZtComponentInfo> curUserRouteAfterLogin = iZtRoleInfoService.getCurUserRouteAfterLogin(userInfo);
        return ZtResBeanEx.ok(curUserRouteAfterLogin);
    }

    Map<String, String> tokenMap = new HashMap<>();

    @ResponseBody
    @RequestMapping(value = "getWeixinConfig", method = RequestMethod.GET)
    public ZtResBeanEx<JSONObject> getWeixinConfig(@RequestParam String url) {
        //url?????????????????????
        JSONObject object = new JSONObject();
        String access_token = tokenMap.get("AccessToken");
        String jsapi_ticket = tokenMap.get("JsapiTicket");
        if (StringUtils.isEmpty(access_token)) {
            //??????access_token
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                    + miniAppId
                    + "&secret="
                    + miniSecret;

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(tokenUrl);
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
            log.info("accessToken");
            log.info(strEntity);
            JSONObject accessObj = JSON.parseObject(strEntity);

            access_token = accessObj.getString("access_token");
            tokenMap.put("AccessToken", access_token);
        }
        if (StringUtils.isEmpty(jsapi_ticket)) {
            //??????jsapi
            String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(jsapiUrl);
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

            log.info("JsapiTicket");
            log.info(strEntity);
            JSONObject jsapi = JSON.parseObject(strEntity);
            jsapi_ticket = jsapi.getString("ticket");
            tokenMap.put("JsapiTicket", jsapi_ticket);

        }
        //????????????signature
        String noncestr = UUID.randomUUID().toString().replace("-", "");
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1??????
        String signature = sha1(str);
        object.put("noncestr", noncestr);
        object.put("timestamp", timestamp);
        object.put("signature", signature);
        object.put("appId", miniAppId);
        object.put("jsapi_ticket", jsapi_ticket);
        log.info("jsapi_ticket=" + jsapi_ticket);
        log.info("noncestr=" + noncestr);
        log.info("timestamp=" + timestamp);
        log.info("url=" + url);
        log.info("signature=" + signature);
        return ZtResBeanEx.ok(object);
    }

    public static String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // ????????????????????? ???????????? ???
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @ApiOperation(value = "??????openid")
    @GetMapping("getOpenIdInXiaoChengXu")
    @ResponseBody
    public ZtResBeanEx<JSONObject> getOpenIdInXiaoChengXu(@RequestParam String code) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + miniAppId + "&secret=" + miniSecret + "&js_code=" + code + "&grant_type=authorization_code";
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
        return ZtResBeanEx.ok(jsonObject);
    }

    @ApiOperation(value = "?????????????????????????????????")
    @PostMapping("getPhoneNumberByAccessToken")
    @ResponseBody
    public ZtResBeanEx<String> getPhoneNumberByAccessToken(@RequestBody JSONObject obj) {
        log.info(JSON.toJSONString(obj));
        String openId = obj.getString("openid");
        String accessToken = obj.getString("access_token");
        String signStr = "signStr";
        String url = cloudFunctionDomain + cloudFunctionPath + "?" + "action=getPhoneNumberByAccessToken&appid=" + cloudFunctionAppId + "&access_token=" + accessToken + "&openid=" + openId + "&sign=" + signStr;
        System.out.println(url);
        String s = HttpUtil.get(url);
        System.out.println(s);
        //{"code":0,"success":true,"phoneNumber":"13812345678"}
        JSONObject jsonObject = JSON.parseObject(s);
        return ZtResBeanEx.ok(jsonObject.get("phoneNumber"));
    }

    @PostMapping("aliPayCallback")
    @ResponseBody
    public String aliPayCallback(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //??????????????????????????????????????????????????????
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            System.out.println(name);
            System.out.println(valueStr);
        }
        return "1success";
    }

}

