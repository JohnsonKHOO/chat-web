package com.a2208.chat.utils;

public class ResultUtil {
    //自定义方法
    public static Result custom(int code, String msg, Object object){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    //成功返回
    public static Result success(Object object){
        return custom(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), object);
    }
    //失败返回
    public static Result fail(){
        return custom(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), null);

    }
    //数据错误返回
    public static Result error(Object object){
        return custom(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMessage(), object);
    }


}