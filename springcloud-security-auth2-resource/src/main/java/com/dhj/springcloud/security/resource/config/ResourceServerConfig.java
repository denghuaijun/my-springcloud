package com.dhj.springcloud.security.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * oauth2资源服务配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    //资源ID要与在授权服务uaa中心配置的客户端信息配置保持一致
    private static final String RESOURCE_ID="res";

    /**
     * 资源服务配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenServices(checkTokenService())
                .stateless(true);
    }

    /**
     * 校验token令牌
     * ruo'zi
     * @return
     */
    private ResourceServerTokenServices checkTokenService() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:10011/oauth/check_token");
        services.setClientId("appid");
        services.setClientSecret("app_secret");
        return services;
    }

    /**
     * 校验客户端的令牌颁发的使用范围
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and().csrf().disable()
                //不记录session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
