package com.a2208.chat.utils;

public class ResultUtil {

    public static Result custom(int code, String msg, Object object){
        Result result = new Result();
        result.setCode(code);
        result.getMsg(msg);
        result.setData(object);
        return result;
    }
    public static Result success(Object object){
        return custom(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), object);
    }

    public static Result fail(){
        return custom(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), null);

    }

    public static Result error(Object object){
        return custom(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMessage(), object);
    }


}