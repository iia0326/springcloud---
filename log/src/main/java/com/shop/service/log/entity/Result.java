package com.shop.service.log.entity;

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
    public Result(int code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public static Result end(int code,Object data,String msg){
        return new Result(code,data,msg);
    }
}
