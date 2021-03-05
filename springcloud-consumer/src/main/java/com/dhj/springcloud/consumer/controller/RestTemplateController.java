package com.dhj.springcloud.consumer.controller;

import com.dhj.springcloud.dao.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 使用restTemplate来进行调用服务端（生产者）
 * 消费者控制层，但是消费者不应有业务层，只是作为客户端进行调用
 */
@RestController
@RequestMapping("/consumer")
public class RestTemplateController {

    //不能注入，说明Springapplication容器中没有这个bean，所以我们要添加一个配置类生成这个bean
    @Autowired
    private RestTemplate restTemplate;//提供多种便捷访问远程http服务的方法，简单的restful模板方法

    //单独使用restTemplate的时候需要使用IP端口来调用，使用eureka里面取服务信息的时候，使用实例ID即可
    //private static final String privider_url="http://localhost:8080/provider";
    private static final String privider_url="http://SPRINGCLOUD-PROVIDER/provider";

    @RequestMapping("/add/dept")
    public String addDept(@RequestBody Dept dept){
        restTemplate.postForObject(privider_url+"/add/dept",dept,String.class);
        return "success";
    }
    @GetMapping("/queryDeptById/{id}")
    public Dept queryDeptById(@PathVariable("id") Long id){
        return restTemplate.getForObject(privider_url+"/queryDeptById/"+id,Dept.class);
    }
    @RequestMapping("/query/all")
    public List<Dept> queryAll(){
        return restTemplate.getForObject(privider_url+"/query/all",List.class);
    }

    /**
     * 测试消费者调用生产端的负载均衡
     * @return
     */
    @RequestMapping("/test/loadbalance")
    public String testLb(){
        return restTemplate.getForObject(privider_url+"/test/loadbalance",String.class);
    }
}
