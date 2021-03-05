package com.dhj.springcloud.fegin;



import com.dhj.springcloud.dao.entity.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 作为fegin客户端调用接口，内部已经继承了ribbon负载均衡机制，其实这一层就相当于生产者中的service接口，我们在实际工作中可以将这倆者结合，提取在一个模块，
 * 然后在生产提供者也引入此依赖
 */
@FeignClient(value = "SPRINGCLOUD-PROVIDER")
public interface DeptFeginService {

    @RequestMapping(value = "/provider/add/dept",method = RequestMethod.POST,consumes = "application/json")
    public void addDept(Dept dept);
    @RequestMapping(value = "/provider/queryDeptById/{id}",method = RequestMethod.GET)
    public Dept queryDeptById(@PathVariable("id") Long id);
    @RequestMapping(value = "/provider/query/all",method = RequestMethod.GET)
    public List<Dept> queryAll();
    @RequestMapping("/provider/test/loadbalance")
    public String testLb();
}
