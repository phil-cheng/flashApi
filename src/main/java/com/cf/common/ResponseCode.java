package com.cf.common;

public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    AuthFail(401, "无效的认证信息"),

    // **** 业务编码 code编号从5000开始 ****
    NotNullFail(5000, "参数必填"),
    NoParams(5003, "参数不能为空"),


    FAIL(500, "未知错误");


    // 状态码和状态信息
    private  int code;
    private  String message;

    // 构造器
    private ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter 方法
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}