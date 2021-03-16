package com.dhj.springcloud.sercurity.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
@MapperScan(basePackages = "com.dhj.springcloud.dao.mapper")
@SpringBootApplication
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.dhj.springcloud.sercurity.uaa")
public class UaaApp {
    public static void main(String[] args) {
        SpringApplication.run(UaaApp.class,args);
    }
}
