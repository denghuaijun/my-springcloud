package com.dhj.springcloud.security.resource.config;

import com.dhj.springcloud.dao.entity.SysPermission;
import com.dhj.springcloud.dao.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * spring security 核心配置类 资源服务也要添加安全访问控制
 */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDao userDao;

/**
     * 要注意设置的前后顺序，放在前面的要具体否则就会被拦截
     * .antMatchers("/res/**").authenticated() 这个放在前面就不会进行后续的权限判断了
     *   //注明拥有权限标识p1才能访问res1资源服务
     *                 .antMatchers("/res/res1").hasAuthority("r1")
     *                 //注明拥有权限标识p2才能访问res2资源服务
     *                 .antMatchers("/res/res2").hasAuthority("r2")
     *                 //将以请求路径以/res开头的请求路径都需要通过认证才能访问，
     *
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.csrf().disable().
//                authorizeRequests();
//
//            //通过数据库来进行web授权
//             List<SysPermission> sysPermissions = userDao.queryAllPermission();
//             sysPermissions.forEach(sysPermission -> {
//                 expressionInterceptUrlRegistry.antMatchers(sysPermission.getUrl()).hasAuthority(sysPermission.getPermTag());
//             });
//             //将以请求路径以/res开头的请求路径都需要通过认证才能访问，
//                expressionInterceptUrlRegistry.antMatchers("/res/**").authenticated()
//                //其它的请求放开权限
//                .anyRequest().permitAll();
      // todo 方法授权使用以下代码
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/res/**").authenticated()
                    .anyRequest().permitAll();
    }
}

