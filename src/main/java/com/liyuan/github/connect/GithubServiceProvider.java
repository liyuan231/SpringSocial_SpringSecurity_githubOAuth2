package com.liyuan.github.connect;

import com.liyuan.github.UrlConstants;
import com.liyuan.github.api.Github;
import com.liyuan.github.api.impl.GithubImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.Assert;

public class GithubServiceProvider<T extends Github> extends AbstractOAuth2ServiceProvider<T> {

    public GithubServiceProvider(String clientId, String clientSecret) {
        super(getOAuth2Template(clientId, clientSecret, UrlConstants.REQUEST_USER_IDENTITY));
    }

    private static OAuth2Template getOAuth2Template(String clientId,
                                                    String clientSecret,
                                                    String REQUEST_USER_IDENTITY) {
        System.out.println("org.springframework.social.oauth2.OAuth2Template");
        System.out.println(clientId);
        OAuth2Template oAuth2Template = new GithubOAuth2Template(clientId,
                clientSecret,
                REQUEST_USER_IDENTITY,
                UrlConstants.ACCESS_TOKEN_API_URL);
        //TODO 这是什么
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }

    @Override
    public T getApi(String accessToken) {
        Assert.notNull(accessToken, "accessToken should not be null!");
        return (T) new GithubImpl(accessToken);
    }
}
