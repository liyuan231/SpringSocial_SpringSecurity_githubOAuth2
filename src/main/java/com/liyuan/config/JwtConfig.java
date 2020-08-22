package com.liyuan.config;

import com.liyuan.model.RestBody;
import com.liyuan.utils.ResponseUtil;
import com.liyuan.utils.jwt.JwtProperties;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import com.liyuan.utils.jwt.JwtTokenPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
@ConditionalOnProperty(prefix = "jwt.config",name = "enabled")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public JwtTokenCacheStorage jwtTokenCacheStorage() {
        return new JwtTokenCacheStorage();
    }

    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtTokenCacheStorage jwtTokenCacheStorage,
                                               JwtProperties jwtProperties) {
        return new JwtTokenGenerator(jwtTokenCacheStorage, jwtProperties);
    }

    @Bean
    public AuthenticationSuccessHandler jwtLoginSuccessHandler(JwtTokenGenerator jwtTokenGenerator) {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                if (response.isCommitted()) {
                    logger.debug("Response has already been commited!");
                    return;
                }
                Map<String, Object> map = new HashMap<>(5);
                map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                map.put("message", "login success!");
                User principle = (User) authentication.getPrincipal();
                String username = principle.getUsername();
                Collection<GrantedAuthority> authorities = principle.getAuthorities();
                Set<String> roles = new HashSet<String>();
                if (!CollectionUtils.isEmpty(authorities)) {
                    for (GrantedAuthority authority : authorities) {
                        String roleName = authority.getAuthority();
                        roles.add(roleName);
                    }
                }
                JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair(username, roles, null);
                String accessToken = jwtTokenPair.getAccessToken();
                String refreshToken = jwtTokenPair.getRefreshToken();
                map.put("access_token", accessToken);
                map.put("refresh_token", refreshToken);
                ResponseUtil.responseJsonWriter(response, RestBody.okData(map, "登录成功！"));
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler(JwtTokenGenerator jwtTokenGenerator) {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                if (response.isCommitted()) {
                    logger.debug("response has been commited!");
                    return;
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                map.put("message", "login failure!");
                ResponseUtil.responseJsonWriter(response, RestBody.build(HttpStatus.UNAUTHORIZED.value(), map, "认证失败", "ERROR"));
            }
        };
    }
}
