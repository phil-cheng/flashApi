package com.cf.exception;

import com.cf.common.ResponseCode;

public class BizException extends RuntimeException {

    private int code;

    public BizException (ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    // Getter 方法
    public int getCode() {
        return code;
    }
}
