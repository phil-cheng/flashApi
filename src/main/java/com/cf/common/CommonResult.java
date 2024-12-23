package com.cf.common;

public class CommonResult<T> {
    private int code;  // 响应状态码
    private String msg;  // 响应消息
    private T data;  // 响应数据

    // 构造器
    public CommonResult() {}
    public CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
