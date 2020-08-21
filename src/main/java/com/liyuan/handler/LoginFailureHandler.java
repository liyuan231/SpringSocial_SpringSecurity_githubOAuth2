package com.liyuan.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这更像一个妥协,为了重写SocialAuthenticationFailureHandler下的SimpleUrlAuthenticationFailureHandler而出此下策
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final String DEFAULT_FAILURE_URL = "/signin";

//    @Autowired
//    public LoginFailureHandler() {
//        super(DEFAULT_FAILURE_URL);
//    }

    /**
     * 这还分阶段的我也是无语
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("com.liyuan.handler.LoginFailureHandler.onAuthenticationFailure1");
//        if (failed instanceof SocialAuthenticationRedirectException) {
//            response.sendRedirect(((SocialAuthenticationRedirectException) failed).getRedirectUrl());
//            return;
//        }
        System.out.println("com.liyuan.handler.LoginFailureHandler.onAuthenticationFailure2");
//        super.onAuthenticationFailure(request, response, failed);
    }
}
