package com.dhj.springcloud.provider.service;

import com.dhj.springcloud.dao.entity.Dept;

import java.util.List;

public interface DeptService {
    public void addDept(Dept dept);
    public Dept queryDeptById(Long id);
    public List<Dept> queryAll();
}
