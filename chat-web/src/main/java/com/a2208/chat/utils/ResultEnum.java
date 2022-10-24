package com.a2208.chat.utils;

public enum ResultEnum {

    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    DATA_ERROR(600,"数据异常");

    private final Integer code;
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

}
