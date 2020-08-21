package com.liyuan.config;

import com.liyuan.handler.LoginFailureHandler;
import com.liyuan.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 因为自动开启了CSRF保护，CsrfFilter因此仅GET，HEAD，TRACE，OPTIONS放行
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("myUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private SpringSocialConfig springSocialConfig;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
//        http.oauth2Login();
        http.authorizeRequests().antMatchers("/signin/**", "/connect/**").permitAll();
//        http.csrf().ignoringAntMatchers("/signin/**");//Csrf放行
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**", "/csrf", "/").permitAll();
        //将SpringSocial加入SpringSecurityFilterChain管理
        http.userDetailsService(userDetailsService);
//        springSocialConfig.alwaysUsePostLoginUrl(true);
//        springSocialConfig.postLoginUrl("/signin/github");
        springSocialConfig.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
            @Override
            public <O extends SocialAuthenticationFilter> O postProcess(O filter) {
                filter.setAuthenticationSuccessHandler(loginSuccessHandler);
                filter.setAuthenticationFailureHandler(new SocialAuthenticationFailureHandler(loginFailureHandler));
                return filter;
            }
        });
        http.apply(springSocialConfig);
//        http.authorizeRequests().anyRequest().authenticated();
//        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/webjars/**", "/js/**", "/image/**", "/img/**", "/images/**");
    }
}
