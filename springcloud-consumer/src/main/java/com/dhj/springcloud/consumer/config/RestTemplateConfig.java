package com.dhj.springcloud.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  //相当于这个类为springmvc中的applicationContext.xml,用来配置bean
public class RestTemplateConfig {

    @Bean
    @LoadBalanced //resttemplate 使用ribbon负载均衡只需要配置此注解即可
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
