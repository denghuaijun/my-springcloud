package com.dhj.springcloud.fallback;


import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.fegin.DeptFeginService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptFeginServiceFallBackFactory implements FallbackFactory {
    @Override
    public DeptFeginService create(Throwable cause) {
      return  new DeptFeginService() {
          @Override
          public void addDept(Dept dept) {

          }

          @Override
          public Dept queryDeptById(Long id) {
              return new Dept().setDbSource("not find mysql...")
                      .setDeptName("id为："+id+"的部门已经关闭，同时此服务API已经做了降级处理。。。")
                      .setDeptNo(id);

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
