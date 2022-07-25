package com.shop.service.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("shop_admin")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不可以为空")
    private String username;
    private Integer roleId;
    private Integer deptId;
    private Integer freeze;
    @NotBlank(message = "密码不可以为空")
    private String password;
    private String nickname;
    private String face;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date insertTime;

    private Integer type;
    private Integer sex;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String roleName;



}
