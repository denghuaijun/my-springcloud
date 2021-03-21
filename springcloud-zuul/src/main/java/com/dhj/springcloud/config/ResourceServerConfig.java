package com.dhj.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * oauth2资源服务配置
 * 网关也是一个资源服务，并管理其它资源服务的权限控制
 */
@Configuration
public class ResourceServerConfig  {
    //资源ID要与在授权服务uaa中心配置的客户端信息配置保持一致
    private static final String RESOURCE_ID="res";

    /**
     * UAA资源服务配置
     */
    @Configuration
    @EnableResourceServer
    public class UaaResourceServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/uaa/**").permitAll();//对于认证中心服务的访问全部放行
        }
    }

    /**
     * UAA资源服务配置
     */
    @Configuration
    @EnableResourceServer
    public class ResResourceServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/res/**").access("#oauth2.hasScope('ROLE_RES')")
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

}
