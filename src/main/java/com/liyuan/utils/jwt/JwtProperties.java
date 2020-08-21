package com.liyuan.utils.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.config")
public class JwtProperties {
    private String issuer;
    private String subscriber;
    private int expireDays;
    private int refreshExpireDays;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public int getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(int expireDays) {
        this.expireDays = expireDays;
    }

    public int getRefreshExpireDays() {
        return refreshExpireDays;
    }

    public void setRefreshExpireDays(int refreshExpireDays) {
        this.refreshExpireDays = refreshExpireDays;
    }
}
