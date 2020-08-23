package com.liyuan.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import com.liyuan.exception.SimpleAuthenticationEntryPoint;
import com.liyuan.model.SysUser;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 拦截jwt认证
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_PREFIX = "Bearer";
    private AuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
    private JwtTokenGenerator jwtTokenGenerator;
    private JwtTokenCacheStorage jwtTokenCacheStorage;

    public JwtAuthenticationTokenFilter(JwtTokenGenerator jwtTokenGenerator, JwtTokenCacheStorage jwtTokenCacheStorage) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtTokenCacheStorage = jwtTokenCacheStorage;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * 已经通过认证！
         */
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(AUTHENTICATION_PREFIX)) {
            String jwtToken = header.substring(AUTHENTICATION_PREFIX.length() + 1);
            if (StringUtils.hasText(jwtToken)) {
                try {
                    authenticationTokenHandle(jwtToken, request);
                } catch (AuthenticationException e) {
                    authenticationEntryPoint.commence(request, response, e);
                }
            } else {
                //有Bearer，没内容
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is not found!"));
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticationTokenHandle(String jwtToken,
                                           HttpServletRequest request) throws BadCredentialsException, CredentialsExpiredException, InternalAuthenticationServiceException {
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(jwtToken);
        if (Objects.nonNull(jsonObject)) {
            //只有username是通用的，是不是意味着需要把username设置为唯一,这里修改了，将audience变为userId，这样就保证是唯一的
            String userId = jsonObject.getString("audience");
            //从缓存中获取token
//            JwtTokenPair jwtTokenPair = jwtTokenCacheStorage.get(username);
            //说实话，缓存什么的还是不大理解，因此就先注释掉
//            if (Objects.isNull(jwtToken)) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("token: " + jwtToken + " is not in cache！");
//                }
//                throw new CredentialsExpiredException("token is not in cache!");
//            }
//            String accessToken = jwtTokenPair.getAccessToken();
//            if (jwtToken.equals(accessToken)) {
            JSONArray authoritiesJSONArray = jsonObject.getJSONArray("authorities");
            Iterator<Object> iterator = authoritiesJSONArray.iterator();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            while (iterator.hasNext()) {
                JSONObject next = (JSONObject) iterator.next();
                authorities.add((GrantedAuthority) () -> next.getString("authority"));
            }
            SysUser user = new SysUser("[USERNAME]", "[PASSWORD]", authorities, userId, null);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("token: " + jwtToken + " did not match!");
            }
            throw new BadCredentialsException("token is invalid!");
        }
//        } else {
//            if (logger.isDebugEnabled()) {
//                logger.debug("token" + jwtToken + "is invalid!");
//            }
//            throw new BadCredentialsException("token is invalid!");
//        }

    }
}
