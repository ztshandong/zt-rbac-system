package com.zhangzhuorui.framework.rbacsystem.exception;

import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.core.ZtResBeanExConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author zhangtao
 * @description 全局异常处理
 * @createDate : 2020-01-01
 **/
@RestControllerAdvice
public class ZtExceptionHandler {

    @Autowired
    ZtResBeanExConfig ztResBeanExConfig;

    protected static final Logger log = LoggerFactory.getLogger(ZtExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ZtResBeanEx handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("handlerMaxUploadSizeExceededException:", e);
        return ZtResBeanEx.error(ztResBeanExConfig.getFailCode(), "上传文件大小不能超过2GB");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ZtResBeanEx handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handlerMethodArgumentNotValidException:", e);
        return ZtResBeanEx.error(ztResBeanExConfig.getFailCode(), "请将数据填写完整");
    }

    @ExceptionHandler(ZtPreAuthorizeException.class)
    public ZtResBeanEx handlerZtPreAuthorizeException(ZtPreAuthorizeException e) {
        log.error("handlerZtPreAuthorizeException:", e);
        return ZtResBeanEx.error(ztResBeanExConfig.getFailCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ZtResBeanEx handlerException(Exception e) {
        log.error("handlerException:", e);
        return ZtResBeanEx.error(ztResBeanExConfig.getFailCode(), "发生错误");
    }

}
