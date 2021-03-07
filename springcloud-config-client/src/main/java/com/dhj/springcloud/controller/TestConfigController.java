package com.dhj.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试配置读取是否从远程仓库中获取
 */
@RestController
public class TestConfigController {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/config")
    public String testConfig(){
        return appName+"----"+serverPort;
    }
}
