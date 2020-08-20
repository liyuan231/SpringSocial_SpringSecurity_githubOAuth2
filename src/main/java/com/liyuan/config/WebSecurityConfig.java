package com.liyuan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 因为自动开启了CSRF保护，CsrfFilter因此仅GET，HEAD，TRACE，OPTIONS放行
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("myUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
//        http.oauth2Login();
        http.authorizeRequests().antMatchers("/signin/**").permitAll();
//        http.csrf().ignoringAntMatchers("/signin/**");//Csrf放行
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**", "/csrf", "/").permitAll();
        //将SpringSocial加入SpringSecurity管理
        http.userDetailsService(userDetailsService);
        SpringSocialConfigurer apply = http.apply(new SpringSocialConfig());
//        apply.
//        http.authorizeRequests().anyRequest().authenticated();
//        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/webjars/**", "/js/**", "/image/**", "/img/**", "/images/**");
    }
    @Bean
    ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator, UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInUtils(factoryLocator, usersConnectionRepository);
    }
}
