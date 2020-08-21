package com.liyuan.github.connect;

import com.liyuan.github.GithubErrorHandler;
import com.liyuan.github.GithubMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GithubOAuth2Template extends OAuth2Template {
    public GithubOAuth2Template(String clientId,
                                String clientSecret,
                                String authorizeUrl,
                                String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    public GithubOAuth2Template(String clientId,
                                String clientSecret,
                                String authorizeUrl,
                                String authenticateUrl,
                                String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl,
                                             MultiValueMap<String, String> parameters) {
        System.out.println("com.liyuan.github.connect.GithubOAuth2Template.postForAccessGrant");
//        parameters.
        return super.postForAccessGrant(accessTokenUrl, parameters);
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken,
                                            String scope,
                                            String refreshToken,
                                            Long expiresIn,
                                            Map<String, Object> response) {
        System.out.println("这里注意！！！org.springframework.social.oauth2.AccessGrant");
        return new GithubAccessGrant(accessToken,scope,refreshToken,expiresIn,(String) response.get("id"));
//        return super.createAccessGrant(accessToken, scope, refreshToken, expiresIn, response);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new GithubMappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new GithubErrorHandler());
        return restTemplate;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return (super.buildAuthorizeUrl(parameters));
    }

    @Override
    public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
        return (super.buildAuthenticateUrl(grantType, parameters));
    }

}
