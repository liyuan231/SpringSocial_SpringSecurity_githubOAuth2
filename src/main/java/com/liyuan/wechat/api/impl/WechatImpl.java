package com.liyuan.wechat.api.impl;

import com.liyuan.wechat.WechatErrorHandler;
import com.liyuan.wechat.WechatMappingJackson2HttpMessageConverter;
import com.liyuan.wechat.api.UserOperations;
import com.liyuan.wechat.api.UserTemplate;
import com.liyuan.wechat.api.Wechat;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WechatImpl extends AbstractOAuth2ApiBinding implements Wechat {
    private UserOperations userOperations;

    public WechatImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        userOperations = new UserTemplate(restOperations(), accessToken);
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public RestOperations restOperations() {
        return getRestTemplate();
    }

    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new WechatErrorHandler());
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
        messageConverters.add(new WechatMappingJackson2HttpMessageConverter());
        return messageConverters;
    }
}
