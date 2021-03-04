package com.dhj.springcloud.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//开启对象链式操作
public class Dept implements Serializable {
    private Long deptNo;
    private String deptName;
    private String dbSource;
}
