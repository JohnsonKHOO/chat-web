package com.a2208.chat.utils;

import lombok.Data;
//定义返回类型Result
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public Result(){
        super();
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}