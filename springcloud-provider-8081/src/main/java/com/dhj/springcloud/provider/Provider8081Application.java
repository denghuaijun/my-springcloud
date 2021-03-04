package com.dhj.springcloud.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan(basePackages = "com.dhj.springcloud.dao.mapper")
@EnableEurekaClient
@EnableDiscoveryClient //获取服务注册信息
@SpringBootApplication
public class Provider8081Application {
    public static void main(String[] args) {
        SpringApplication.run(Provider8081Application.class,args);
    }
}
