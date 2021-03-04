package com.dhj.springcloud.provider.controller;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.provider.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class DeptController {

    @Value("${server.port}") //用来测试负载均衡的
    private String serverPort;

    @Autowired
    private DeptService deptService;

    @RequestMapping("/add/dept")
    public String addDept(@RequestBody Dept dept){
         deptService.addDept(dept);
         return "success";
    }
    @GetMapping("/queryDeptById/{id}")
    public Dept queryDeptById(@PathVariable("id") Long id){
        return deptService.queryDeptById(id);
    }
    @RequestMapping("/query/all")
    public List<Dept> queryAll(){
        return deptService.queryAll();
    }

    @RequestMapping("/test/loadbalance")
    public String testLb(){
        return "你正在访问的生产者的端口:"+serverPort;
    }
}
