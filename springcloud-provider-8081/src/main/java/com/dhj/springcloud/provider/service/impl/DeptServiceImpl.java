package com.dhj.springcloud.provider.service.impl;

import com.dhj.springcloud.dao.entity.Dept;
import com.dhj.springcloud.dao.mapper.DeptMapper;
import com.dhj.springcloud.provider.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired(required = false)
    private DeptMapper deptMapper;

    @Override
    public void addDept(Dept dept) {
         deptMapper.addDept(dept);
    }

    @Override
    public Dept queryDeptById(Long id) {
        return deptMapper.queryDeptById(id);
    }

    @Override
    public List<Dept> queryAll() {
        return deptMapper.queryAll();
    }
}
