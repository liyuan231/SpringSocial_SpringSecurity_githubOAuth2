package com.liyuan.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class SpringSecuritySignInAdapter implements SignInAdapter {

    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        System.out.println("com.liyuan.config.SpringSecuritySignInAdapter");
        System.out.println(localUserId);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(localUserId, null, null));
        return "/static/login.html";
    }
}
