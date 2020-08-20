package com.liyuan.github.api.impl;

import com.liyuan.github.GithubErrorHandler;
import com.liyuan.github.GithubMappingJackson2HttpMessageConverter;
import com.liyuan.github.api.Github;
import com.liyuan.github.api.UserOperations;
import com.liyuan.github.api.UserTemplate;
import com.liyuan.wechat.WechatMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GithubImpl extends AbstractOAuth2ApiBinding implements Github {
    private UserOperations userOperations;

    public GithubImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        userOperations = new UserTemplate(restOperations(), accessToken);
    }

    public UserOperations userOperations() {
        return userOperations;
    }

    public RestOperations restOperations() {
        return getRestTemplate();
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new GithubErrorHandler());
        super.configureRestTemplate(restTemplate);
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(3);
//        messageConverters.add(new StringHttpMessageConverter());
//        messageConverters.add(getFormMessageConverter());
//        messageConverters.add(getJsonMessageConverter());
//        messageConverters.add(getByteArrayMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new FormMapHttpMessageConverter());
        messageConverters.add(new GithubMappingJackson2HttpMessageConverter());
        messageConverters.add(getFormMessageConverter());
        messageConverters.add(getJsonMessageConverter());
        messageConverters.add(getByteArrayMessageConverter());
        return messageConverters;
    }

}
