package com.dhj.springcloud.provider;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.provider.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ProviderApplication.class)
public class SpringBootTests {
    @Autowired
    private DeptService deptService;
    @Test
    public void test1(){
        List<Dept> depts = deptService.queryAll();
        System.out.println(depts);
    }

}
