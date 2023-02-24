package com.yupi.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String userAccount;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String phone;

    /**
     * 0:normal
     */
    private Integer userStatus;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private Date createTime;
    /**
     *0 普通用户 1 管理员
     */
    private Integer userRole;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}