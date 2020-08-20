package com.liyuan;

import com.liyuan.wechat.autoconfiguration.WechatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.social.config.annotation.EnableSocial;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSocial
@EnableWebSecurity
@EnableSwagger2
@Import({WechatAutoConfiguration.class})
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
//    @Bean
//    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
//                                                             UsersConnectionRepository usersConnectionRepository, WechatSignInAdapter wechatSignInAdapter) {
//        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
//                .setConnectionSignUp((Connection<?> connection) -> connection.getKey().getProviderUserId());
//        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, wechatSignInAdapter);
//    }
}
