package com.liyuan.github.connect;

import com.liyuan.github.api.Github;
import com.liyuan.github.api.GithubAdapter;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 第一步，配置一个ConnectionFactory
 */
public class GithubConnectionFactory extends OAuth2ConnectionFactory<Github> {
    public GithubConnectionFactory(String clientId, String clientSecret) {
        this(clientId, clientSecret, new GithubAdapter());
    }

    public GithubConnectionFactory(String clientId, String clientSecret, ApiAdapter<Github> apiAdapter) {
        super("github", new GithubServiceProvider<>(clientId, clientSecret), apiAdapter);
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return ((GithubAccessGrant) accessGrant).getId();
    }
}
