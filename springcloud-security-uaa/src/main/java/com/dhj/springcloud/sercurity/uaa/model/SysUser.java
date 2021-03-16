package com.dhj.springcloud.sercurity.uaa.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String realname;
    private Date createDate;
    private Date lastLoginTime;
    private Integer enabled;
    private Integer accountNonExpired;
    private Integer accountNonLocked;
    private Integer credentialsNonExpired;

}
