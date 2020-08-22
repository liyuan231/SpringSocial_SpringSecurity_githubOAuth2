package com.liyuan.config;

import com.liyuan.handler.SimpleAccessDeniedHandler;
import com.liyuan.exception.SimpleAuthenticationEntryPoint;
import com.liyuan.filter.JwtAuthenticationTokenFilter;
import com.liyuan.handler.CustomLogoutSuccessHandler;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 因为自动开启了CSRF保护，CsrfFilter因此仅GET，HEAD，TRACE，OPTIONS放行
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("myUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private SpringSocialConfig springSocialConfig;

    @Autowired
    AuthenticationSuccessHandler jwtLoginSuccessHandler;
    @Autowired
    AuthenticationFailureHandler jwtLoginFailureHandler;

    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    JwtTokenCacheStorage jwtTokenCacheStorage;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(jwtLoginSuccessHandler)
                .failureHandler(jwtLoginFailureHandler);
        http.logout().logoutSuccessHandler(new CustomLogoutSuccessHandler());
//        http.oauth2Login();
        http.authorizeRequests().antMatchers("/signin/**", "/connect/**").permitAll();
//        http.csrf().ignoringAntMatchers("/signin/**");//Csrf放行
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**", "/csrf", "/").permitAll();
        //将SpringSocial加入SpringSecurityFilterChain管理
        http.userDetailsService(userDetailsService);
//        springSocialConfig.alwaysUsePostLoginUrl(true);
//        springSocialConfig.postLoginUrl("/signin/github");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().accessDeniedHandler(new SimpleAccessDeniedHandler()).authenticationEntryPoint(new SimpleAuthenticationEntryPoint());
        http.addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenGenerator, jwtTokenCacheStorage), UsernamePasswordAuthenticationFilter.class);
        springSocialConfig.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
            @Override
            public <O extends SocialAuthenticationFilter> O postProcess(O filter) {
                filter.setAuthenticationSuccessHandler(jwtLoginSuccessHandler);
                filter.setAuthenticationFailureHandler(new SocialAuthenticationFailureHandler(jwtLoginFailureHandler));
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
