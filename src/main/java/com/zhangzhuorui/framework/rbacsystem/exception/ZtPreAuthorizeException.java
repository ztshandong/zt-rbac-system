package com.zhangzhuorui.framework.rbacsystem.exception;

import com.zhangzhuorui.framework.core.ZtStrUtils;

public class ZtPreAuthorizeException extends RuntimeException {

    private String code = ZtStrUtils.FAIL_CODE;

    public ZtPreAuthorizeException() {
    }

    public ZtPreAuthorizeException(String msg) {
        super(msg);
    }

    public ZtPreAuthorizeException(String code, String message) {
        super(message);
        this.code = code;
    }

}
