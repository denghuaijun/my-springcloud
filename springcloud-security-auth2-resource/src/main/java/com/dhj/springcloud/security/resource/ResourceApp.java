package com.dhj.springcloud.security.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = {"com.dhj.springcloud.dao.mapper"})
@ComponentScan(basePackages = {"com.dhj.springcloud.dao.repository","com.dhj.springcloud.security.resource"})
@SpringBootApplication
@EnableHystrix
@EnableDiscoveryClient
@EnableEurekaClient
public class ResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApp.class,args);
    }
}
