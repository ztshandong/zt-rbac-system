package com.zhangzhuorui.framework.rbacsystem.aspects;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @author :  zhangtao
 * @createDate :  2021/8/21 上午10:34
 * @return :
 * @description :  日志切面
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Component
@Aspect
@Slf4j
@Order(-5)
@CrossOrigin
public class ZtLogAspect {

    @Pointcut("execution(public * com.zhangzhuorui..*.controller..*.*(..))")
    public void ztLog() {
    }

    @Before("ztLog()")
    public void before(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String s1 = StringUtils.join(args, " ; ");
        try {
            s1 = JSON.toJSONString(args);
        } catch (Exception e) {
        }

        log.info("{}.{} : 请求参数:{}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), s1);

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String s = enu.nextElement();
            log.info("具体参数：" + s + ":" + request.getParameter(s));
        }
    }

}
