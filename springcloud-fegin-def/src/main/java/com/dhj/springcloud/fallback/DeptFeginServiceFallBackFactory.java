package com.dhj.springcloud.fallback;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.fegin.DeptFeginService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptFeginServiceFallBackFactory implements FallbackFactory {
    @Override
    public Object create(Throwable cause) {
        return new DeptFeginService() {
            @Override
            public void addDept(Dept dept) {

            }

            @Override
            public Dept queryDeptById(Long id) {
                return null;
            }

            @Override
            public List<Dept> queryAll() {
                return null;
            }

            @Override
            public String testLb() {
                return null;
            }

            @Override
            public Dept queryDeptByIdByHystrix(Long id) {
                return null;
            }
        };
    }
}
