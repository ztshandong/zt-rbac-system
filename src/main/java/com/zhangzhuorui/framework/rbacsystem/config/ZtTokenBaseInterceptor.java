package com.zhangzhuorui.framework.rbacsystem.config;

import com.zhangzhuorui.framework.rbacsystem.exception.ZtPreAuthorizeException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ZtTokenBaseInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL = request.getRequestURI();
        if (ztJwtTokenUtil.getIgnoreUrlLists().contains(requestURL)) {
            return true;
        }
        String zttoken = request.getHeader(ztJwtTokenUtil.getTokenHeader());
        if (StringUtils.isEmpty(zttoken)) {
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getRequestURL-----------" + request.getRequestURL());
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getQueryString-----------" + request.getQueryString());
            log.warn("ZtTokenBaseInterceptor-----未传token");
            throw new ZtPreAuthorizeException("未传token");
        }
        Claims claim = ztJwtTokenUtil.getClaimByToken(zttoken);
        if (null == claim) {
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getRequestURL-----------" + request.getRequestURL());
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getQueryString-----------" + request.getQueryString());
            log.warn("ZtTokenBaseInterceptor-----token无效");
            throw new ZtPreAuthorizeException("token无效");
        }

        boolean tokenExpired = ztJwtTokenUtil.validateToken(zttoken);
        if (tokenExpired) {
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getRequestURL-----------" + request.getRequestURL());
            System.out.println(LocalDateTime.now() + "ZtTokenBaseInterceptor  getQueryString-----------" + request.getQueryString());
            log.warn("ZtTokenBaseInterceptor-----token过期");
            throw new ZtPreAuthorizeException("token过期");
        }

        return true;
    }
}
