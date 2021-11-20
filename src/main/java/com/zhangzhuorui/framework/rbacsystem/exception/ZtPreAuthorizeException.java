package com.zhangzhuorui.framework.rbacsystem.exception;

import com.zhangzhuorui.framework.core.ZtResBeanExConfig;
import com.zhangzhuorui.framework.core.ZtSpringUtil;

public class ZtPreAuthorizeException extends RuntimeException {

    private String code;

    public ZtPreAuthorizeException() {
    }

    public ZtPreAuthorizeException(String msg) {
        super(msg);
        this.code = ZtSpringUtil.getBeanFactory().getBean(ZtResBeanExConfig.class).getFailCode();
    }

    public ZtPreAuthorizeException(String code, String message) {
        super(message);
        this.code = code;
    }

}
