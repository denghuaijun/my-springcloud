package com.dhj.springcloud.sercurity.uaa.config;

import com.dhj.springcloud.sercurity.uaa.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;
    //认证管理器时在密码模式要使用，这个需要在springsecurity配置类中进行配置
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 配置客户端详情信息，你可以把客户端信息初始化在这里，可以写死，也可以从数据库中调取信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //先将客户端信息写死在内存中,可以通过and进行多客户端配置
        clients.inMemory()
                .withClient("appid")//客户端唯一标识
                .secret(new BCryptPasswordEncoder().encode("app_secret"))//客户端密钥，这个需要加密的
                .resourceIds("res")//客户端访问的资源服务ID，在资源服务配置类里面声明
                .scopes("all")//允许客户端访问的范围，若是为空，则默认客户端可以拥有全部范围的访问权限
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")//客户端认证授权方式，默认五种
                .autoApprove(false)//这个是只有授权方式为授权码时可用，若为false则，会有一个授权页面
                //.authorities() 此客户端可以使用的权限，一般不需要使用到这个属性配置
                .redirectUris("http://localhost:10011");//验证回调地址
    }

    /**
     * 配置令牌服务
     */
    @Bean
    public AuthorizationServerTokenServices  tokenServices(){
        DefaultTokenServices defaultTokenServices=new DefaultTokenServices();
//        defaultTokenServices.setAuthenticationManager(authenticationManager);
        defaultTokenServices.setClientDetailsService(clientDetailsService);//客户端详情服务
        defaultTokenServices.setTokenStore(tokenStore);//令牌持久化策略
        defaultTokenServices.setSupportRefreshToken(true);//支持刷新令牌
        defaultTokenServices.setAccessTokenValiditySeconds(7200);//令牌默认有效时间2h
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);//刷新令牌有效期3天
        return defaultTokenServices;
    }

    /**
     * 设置授权码模式的授权码存取方式，暂时采用默认的内存方式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }
    /**
     * 配置令牌访问端点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //当你选择了资源所有者密码授权类型的时候，必须要设置认证管理器
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //存储的认证的用户信息
                //选择授权码模式需要配置
                .authorizationCodeServices(authorizationCodeServices)
                //令牌服务管理
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        //endpoints.userDetailsService(new MyUserDetailsService)//如果你设置了这个属性话，说明你有一个自己的UserDetailsService实现
    }

    /**
     * 配置令牌接口访问约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()") //对/oauth/check_token请求放开不需要认证
                .tokenKeyAccess("permitAll()") //对于 /oauth/token_key 请求公钥的key接口放开权限
                .allowFormAuthenticationForClients();
    }
}
