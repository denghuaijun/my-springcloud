package com.dhj.springcloud.sercurity.uaa.dao;


import com.dhj.springcloud.sercurity.uaa.model.SysPermission;
import com.dhj.springcloud.sercurity.uaa.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public SysUser querySysUser(String userName){
        String sql="select * from sys_user where username=? ";
        List<SysUser> query = jdbcTemplate.query(sql,  new BeanPropertyRowMapper<SysUser>(SysUser.class),new Object[]{userName});
        if (!CollectionUtils.isEmpty(query)){
            return query.get(0);
        }
        return null;
    };

    /**
     * 查询所有权限列表
     * @return
     */
    public  List<SysPermission> queryAllPermission(){
        String sql="select * from sys_permission";
        List<SysPermission> query = jdbcTemplate.query(sql,  new BeanPropertyRowMapper<SysPermission>(SysPermission.class));

        return query;
    };
    /**
     * 根据用户查询用户对应的权限列表
     * @return
     */
    public List<SysPermission> queryPermissionListByName(String userName){
        String sql="SELECT\n" +
                "  sp.*\n" +
                "FROM\n" +
                "  sys_permission sp\n" +
                "  INNER JOIN sys_role_permission srp\n" +
                "    ON sp.`id` = srp.`perm_id`\n" +
                "  INNER JOIN sys_role sr\n" +
                "    ON srp.`role_id` = sr.`id`\n" +
                "  INNER JOIN sys_user_role sur\n" +
                "    ON sur.`role_id` = sr.`id`\n" +
                "  INNER JOIN sys_user su\n" +
                "    ON su.`id` = sur.`user_id`\n" +
                "WHERE su.`username` = ?;\n" +
                "\n";
        List<SysPermission> query = jdbcTemplate.query(sql,  new BeanPropertyRowMapper<SysPermission>(SysPermission.class),new Object[]{userName});
        return query;

    }
    public List<Map<String, Object>> queryList(){
        List<Map<String, Object>> map = jdbcTemplate.queryForList("SELECT * FROM sys_user;");
        return map;
    }
}
