package com.dhj.springcloud.consumer.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  //相当于这个类为springmvc中的applicationContext.xml,用来配置bean
public class RestTemplateConfig {

    //其实负载均衡是实现了IRule接口
    //RandomRule
    //RoundRobinRule 默认是轮询，若要修改为随机则如下
    //WeightedResponseTimeRule
    //RetryRule
    @Bean
    @LoadBalanced //resttemplate 使用ribbon负载均衡只需要配置此注解即可
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean //这样就修改为随机的负载策略
    public IRule randomRule(){
        return new RandomRule();
    }

    //若要自定义修改负载均衡策略，那么只需要在与主启动类不同级的包下面，增加@configuration注解的类学习如RandomRule重写里面的choose方法即可
}
