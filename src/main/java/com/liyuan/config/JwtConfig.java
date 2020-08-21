package com.liyuan.config;

import com.liyuan.utils.jwt.JwtProperties;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt.config")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Bean
    public JwtTokenCacheStorage jwtTokenCacheStorage() {
        return new JwtTokenCacheStorage();
    }
    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtTokenCacheStorage jwtTokenCacheStorage,
                                               JwtProperties jwtProperties){
        return new JwtTokenGenerator(jwtTokenCacheStorage,jwtProperties);
    }
}
