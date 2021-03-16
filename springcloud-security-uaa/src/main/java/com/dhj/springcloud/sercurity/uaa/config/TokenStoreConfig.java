package com.dhj.springcloud.sercurity.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 进行管理令牌存储策略，对于TokenStore实例的生成
 */
@Configuration
public class TokenStoreConfig {

    @Bean
    public TokenStore tokenStore(){
        //默认时基于内存存储，还有jdbc及jwt方式的存储，也可以在这里面自定义存储方式如redis
        return new InMemoryTokenStore();
    }

}
