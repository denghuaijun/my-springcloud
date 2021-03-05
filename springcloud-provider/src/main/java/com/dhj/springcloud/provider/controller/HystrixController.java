package com.dhj.springcloud.provider.controller;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.provider.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试断路器hystrix
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {
    @Autowired
    private DeptService  deptService;



    @GetMapping("/queryDeptById/{id}")
    @HystrixCommand(fallbackMethod = "testHystrix")
    public Dept queryDeptById(@PathVariable("id") Long id){
        Dept dept = deptService.queryDeptById(id);
        if (dept == null){
            throw new RuntimeException("手动抛出没有相关部门信息异常");
        }
        return dept;
    }

    /**
     * 与实际的服务方法一样，熔断方法
     * @param id
     * @return
     */
    public Dept testHystrix(@PathVariable("id") Long id){
    return new Dept().setDeptNo(id)
            .setDeptName("id---"+id+"对应的部门信息不存在，@Hystrix")
            .setDbSource("no this datasource in mysql..");
    }

}
