package com.dhj.springcloud.sercurity.uaa.service;



import com.alibaba.fastjson.JSON;
import com.dhj.springcloud.dao.entity.SysPermission;
import com.dhj.springcloud.dao.entity.SysUser;
import com.dhj.springcloud.dao.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    /**
     * 拿输入的用户名密码和数据库已经使用BCrpt加密的密码校验
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = userDao.querySysUser(userName);

        if (sysUser == null){
            throw new RuntimeException("用户不存在");
        }
        List<SysPermission> sysPermissions = userDao.queryPermissionListByName(userName);
        List<String> stringList = new ArrayList<>();
        sysPermissions.forEach(sysPermission -> stringList.add(sysPermission.getPermTag()) );
        String[] sysPerArray=new String[stringList.size()];
        String[] strings = stringList.toArray(sysPerArray);
        //为了方便在后续使用用户所有信息所以在这边直接把用户对象的json串传递出去
        String priciple = JSON.toJSONString(sysUser);
        UserDetails build = User.withUsername(priciple).password(sysUser.getPassword()).authorities(strings).build();
        return build;
    }
}
