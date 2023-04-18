package com.vms.common;
import lombok.Data;

@Data
public class Result {
    private int code; //编码 200。400

    private String msg;//成功失败

    private long total;//总记录数

    private Object data;//数据

    public static Result fail(){
        return result(400,"失败",0L,null);
    }

    public static Result success(Object data,long total){
        return result(200,"成功",total,data);
    }

    private static Result result(int code, String msg, long total, Object data){
        Result result = new Result();
        result.setData(data);
        result.setMsg(msg);
        result.setTotal(total);
        result.setCode(code);
        return result;
    }
}
