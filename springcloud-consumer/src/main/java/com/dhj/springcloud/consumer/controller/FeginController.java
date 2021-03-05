package com.dhj.springcloud.consumer.controller;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.fegin.DeptFeginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 使用feginClien接口来进行调用服务端（生产者）
 * 消费者控制层，但是消费者不应有业务层，只是作为客户端进行调用
 */
@RestController
@RequestMapping("/feign/consumer")
public class FeginController {

    //注入feginClient接口类
    @Autowired
    private DeptFeginService deptFeginService;


    @RequestMapping("/add/dept")
    public String addDept(@RequestBody Dept dept){
        deptFeginService.addDept(dept);
        return "success";
    }
    @GetMapping("/queryDeptById/{id}")
    public Dept queryDeptById(@PathVariable("id") Long id){
        return deptFeginService.queryDeptById(id);
    }
    @RequestMapping("/query/all")
    public List<Dept> queryAll(){
        return deptFeginService.queryAll();
    }

    /**
     * 测试消费者调用生产端的负载均衡
     * @return
     */
    @RequestMapping("/test/loadbalance")
    public String testLb(){
        return deptFeginService.testLb();
    }

    /**
     * 测试服务端熔断
     * @param id
     * @return
     */
    @GetMapping("/hystrix/queryDeptById/{id}")
    public Dept queryDeptByIdByHystrix(@PathVariable("id") Long id){
        return deptFeginService.queryDeptByIdByHystrix(id);
    }

}
