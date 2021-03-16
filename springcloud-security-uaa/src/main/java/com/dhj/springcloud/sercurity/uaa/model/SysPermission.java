package com.dhj.springcloud.sercurity.uaa.model;

import lombok.Data;

@Data
public class SysPermission {
    private Long id;
    private String permName;
    private String permTag;
    private String url;
}
