package com.dhj.springcloud.sercurity.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //注册认证管理器
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
       return super.authenticationManager();
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //安全拦截约束配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/res/res1").hasAuthority("res1")
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();

    }

    public static void main(String[] args) {
        String encode = BCrypt.hashpw("123",BCrypt.gensalt());
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$JLzIPolj43RLsaS7rf1bEeaRqg82rbHZFi0R1W6EF21");
        System.out.println(checkpw);
        System.out.println(encode);
    }
}
