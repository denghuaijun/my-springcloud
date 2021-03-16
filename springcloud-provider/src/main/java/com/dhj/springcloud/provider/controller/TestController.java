package com.dhj.springcloud.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myprovider")
public class TestController {

    @RequestMapping("/test/zuul")
    public String testZuul(){
        return "66666666";
    }
}
