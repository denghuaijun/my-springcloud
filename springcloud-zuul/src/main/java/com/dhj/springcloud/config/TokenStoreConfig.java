package com.dhj.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 进行管理令牌存储策略，对于TokenStore实例的生成
 */
@Configuration
public class TokenStoreConfig {

    //jwt密钥
    private static final String JWT_SIGN_KEY="jwt_sign_key";

    @Bean
    public TokenStore tokenStore(){
        //默认时基于内存存储，还有jdbc及jwt方式的存储，也可以在这里面自定义存储方式如redis
        //return new InMemoryTokenStore();
        //使用JWT令牌存储
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JWT_SIGN_KEY); //对称密钥，资源服务使用该密钥来验证
        return jwtAccessTokenConverter;
    }

}
