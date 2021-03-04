package com.dhj.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka遵循ap原则   可用性，容错性
 * zookeeper 遵循cp原则 一致性，容错性
 */
@EnableEurekaServer
@SpringBootApplication
public class Eureka8761Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka8761Application.class,args);
    }
}
