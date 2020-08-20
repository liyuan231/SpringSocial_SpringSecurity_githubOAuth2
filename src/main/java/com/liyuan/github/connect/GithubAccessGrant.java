package com.liyuan.github.connect;

import org.springframework.social.oauth2.AccessGrant;

public class GithubAccessGrant extends AccessGrant {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GithubAccessGrant(String accessToken,
                             String scope,
                             String refreshToken,
                             Long expiresIn,
                             String id) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.id = id;
    }

}
