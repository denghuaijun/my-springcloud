package com.dhj.springcloud.security.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResController {

    @RequestMapping(value = "/res/res1",produces = "text/plain;charset=utf-8")
    @PreAuthorize("hasAuthority('res1')")
    //这里面有一个坑，就是接口方法不能写成private，不然权限限制没有效果
    public String res1(){
        return "访问资源服务1成功";
    }
}
