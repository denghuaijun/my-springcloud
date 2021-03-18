package com.dhj.springcloud.security.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * oauth2资源服务配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    //资源ID要与在授权服务uaa中心配置的客户端信息配置保持一致
    private static final String RESOURCE_ID="res";

    @Autowired
    private TokenStore tokenStore;

    /**
     * 资源服务配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                //.tokenServices(checkTokenService()) //远程uaa服务端校验token
                .tokenStore(tokenStore)//使用jwt令牌就可以将资源服务令牌校验配置类注销
                .stateless(true);
    }

    /**
     * 校验token令牌 使用内存存储令牌要定义这个校验token服务的方式。
     * ruo'zi
     * @return
     */
//    private ResourceServerTokenServices checkTokenService() {
//        //使用远程服务请求认证授权中心校验token,同时必须要输入校验地址及appid，appkey，这样有个缺点，若访问量大这个就影响性能，使用JWT令牌就可以解决
//        //后期替换成JWT就不需要重复去请求认证中心了
//        RemoteTokenServices services = new RemoteTokenServices();
//        services.setCheckTokenEndpointUrl("http://localhost:10011/oauth/check_token");
//        services.setClientId("appid");
//        services.setClientSecret("app_secret");
//        return services;
//    }

    /**
     * 校验客户端的令牌颁发的使用范围
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_RES')")
                .and().csrf().disable()
                //不记录session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
