package com.shop.service.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_log")
public class LogEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String action;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;

//    private String path;

    private String ip;

    private String url;

    private String method;

    private Long userId;

    private String request;

    private String type;

    private String module;

//    private String userAccount;

    private String username;

    private String nickname;


//    private String response;

//    private Integer statusCode;


    private String actionName;

}
