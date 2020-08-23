package com.liyuan.utils.jwt;

import com.liyuan.model.RestBody;
import com.liyuan.model.SysUser;
import com.liyuan.utils.ResponseUtil;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "jwt.config", name = "enabled")
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {
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
                SysUser sysUser = (SysUser) authentication.getPrincipal();
                Collection<GrantedAuthority> authorities = sysUser.getAuthorities();
                /**
                 * 将userId作为audience，保证唯一
                 */
                JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair(sysUser.getUserId(), authorities, null);
                String accessToken = jwtTokenPair.getAccessToken();
                String refreshToken = jwtTokenPair.getRefreshToken();
                map.put("access_token", accessToken);
                map.put("refresh_token", refreshToken);
                ResponseUtil.responseJsonWriter(response, RestBody.okData(map, "登录成功！"));
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler jwtLoginFailureHandler(JwtTokenGenerator jwtTokenGenerator) {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                if (response.isCommitted()) {
                    logger.debug("response has been commited!");
                    return;
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                map.put("message", "login failure!");
                map.put("exception", e.getCause());
                ResponseUtil.responseJsonWriter(response, RestBody.build(HttpStatus.UNAUTHORIZED.value(), map, "认证失败", "ERROR"));
            }
        };
    }

    @Bean
    public LogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                ResponseUtil.responseJsonWriter(response, RestBody.ok("退出成功！"));
            }
        };
    }
}
