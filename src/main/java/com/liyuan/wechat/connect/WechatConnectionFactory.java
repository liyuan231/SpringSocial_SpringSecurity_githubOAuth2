package com.liyuan.wechat.connect;

import com.liyuan.wechat.api.Wechat;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

public class WechatConnectionFactory extends OAuth2ConnectionFactory<Wechat> {
    public WechatConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    public WechatConnectionFactory(String appId, String appSecret, ApiAdapter<Wechat> apiAdapter) {
        super("wechat", new WechatServiceProvider<Wechat>(appId, appSecret), apiAdapter);
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return ((WechatAccessGrant) accessGrant).getOpenid();
    }
}
