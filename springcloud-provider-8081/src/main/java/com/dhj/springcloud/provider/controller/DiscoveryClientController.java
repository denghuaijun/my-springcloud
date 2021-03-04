package com.dhj.springcloud.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取注册到eureka中的服务实例列表信息
 */
@RestController
public class DiscoveryClientController {

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/get/disCoveryList")
    public String getDiscoveryList(){
        //获取所有的注册服务信息
        List<String> services = discoveryClient.getServices();
        //获取指定的注册信息根据service-id
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("SPRINGCLOUD-PROVIDER");
        serviceInstanceList.forEach(serviceInstance -> {
            System.out.println("服务主机信息host："+serviceInstance.getHost());
            System.out.println("服务主机信息instanceId："+serviceInstance.getInstanceId());
            System.out.println("服务主机信息uri："+serviceInstance.getUri());
        });
        return services.toString();
    }
}
