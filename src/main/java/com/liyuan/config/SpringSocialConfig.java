package com.liyuan.config;

import com.liyuan.github.AccountConnectionSignUp;
import com.liyuan.social.jdbc.TuneJdbcUsersConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class SpringSocialConfig extends SpringSocialConfigurer {
    @Autowired
    DataSource dataSource;
    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    @Primary
    @Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
    public UsersConnectionRepository jdbcUsersConnectionRepository() {
        TuneJdbcUsersConnectionRepository tuneJdbcUsersConnectionRepository = new TuneJdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        tuneJdbcUsersConnectionRepository.setConnectionSignUp(new AccountConnectionSignUp(jdbcTemplate));
        return tuneJdbcUsersConnectionRepository;

    }

    @Bean
    public ProviderSignInController providerSignInController(
            ConnectionFactoryLocator connectionFactoryLocator,
            UsersConnectionRepository usersConnectionRepository) {
        ProviderSignInController controller = new ProviderSignInController(
                connectionFactoryLocator,
                jdbcUsersConnectionRepository(),
                new SpringSecuritySignInAdapter());
//        controller.setSignUpUrl("/register");
        return controller;
    }

}
