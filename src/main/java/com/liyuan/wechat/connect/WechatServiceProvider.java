package com.liyuan.wechat.connect;

import com.liyuan.wechat.UrlConstants;
import com.liyuan.wechat.api.Wechat;
import com.liyuan.wechat.api.impl.WechatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.Assert;

public class WechatServiceProvider<T extends Wechat> extends AbstractOAuth2ServiceProvider<T> {
    /**
     * 通过appId以及appSecret获取登录二维码
     *
     * @param appId
     * @param appSecret
     */
    public WechatServiceProvider(String appId, String appSecret) {
        this(appId, appSecret, UrlConstants.QRCONNECT_API_URL);
    }

    /**
     * 续上同时封装了通过二维码Code再通过code获取access_token
     *
     * @param appId
     * @param appSecret
     * @param qrconnectApiUrl
     */
    public WechatServiceProvider(String appId, String appSecret, String qrconnectApiUrl) {
        super(getOAuth2Template(appId, appSecret, qrconnectApiUrl));
    }

    private static OAuth2Template getOAuth2Template(String appId,
                                                    String appSecret,
                                                    String qrconnectApiUrl) {
        OAuth2Template oAuth2Template = new WechatOAuth2Template(appId, appSecret, qrconnectApiUrl, UrlConstants.ACCESS_TOKEN_API_URL);
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }

    @Override
    public T getApi(String accessToken) {
        Assert.notNull(accessToken, "accessToken should not be null!");
        return (T) new WechatImpl(accessToken);
    }
}
