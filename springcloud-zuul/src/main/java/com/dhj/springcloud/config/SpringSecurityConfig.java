package com.dhj.springcloud.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring security 核心配置类 网关服务也要添加安全访问控制。这里针对所有请求放行即可
 * 访问网关直接放行即可，具体的权限控制放在访问具体资源服务器时
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //网关全部放行
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll();
    }
}

