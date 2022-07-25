package com.kangyi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    private int code;
    private Object data;
    private String msg;
    private static Result result;
    public Result(int code,Object data,String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public static Result end(int code,Object data,String msg){
        if(result == null){
            return new Result(code,data,msg);
        }else{
            result.code = code;
            result.data = data;
            result.msg = msg;
            return result;
        }
    }
}
