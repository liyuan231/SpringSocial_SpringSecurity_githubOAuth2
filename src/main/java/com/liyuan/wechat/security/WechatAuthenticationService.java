package com.liyuan.wechat.security;

import com.liyuan.wechat.api.Wechat;
import com.liyuan.wechat.connect.WechatConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class WechatAuthenticationService extends OAuth2AuthenticationService<Wechat> {
    public WechatAuthenticationService(final String apiKey, final String appSecret) {
         super(new WechatConnectionFactory(apiKey, appSecret));
    }
}
