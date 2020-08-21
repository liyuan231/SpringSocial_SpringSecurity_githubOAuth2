package com.liyuan.utils.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt.config")
public class JwtConfiguration {
//    @Bean
//    public JwtTokenGenerator jwtTokenGenerator(JwtProperties jwtProperties){
//        return new JwtTokenGenerator(jwtProperties);
//}
}
