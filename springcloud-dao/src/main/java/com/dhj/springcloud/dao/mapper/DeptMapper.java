package com.dhj.springcloud.dao.mapper;

import com.dhj.springcloud.dao.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeptMapper {
    public void addDept(Dept dept);
    public Dept queryDeptById(Long id);
    public List<Dept> queryAll();
}
