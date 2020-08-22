package com.liyuan.utils.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.config")
public class JwtProperties {
    private boolean enabled;
    private String keyLocation;
    private String keyAlias;
    private String keyPass;
    private String issuer;
    private String subscriber;
    private int accessExpireDays;
    private int refreshExpireDays;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKeyLocation() {
        return keyLocation;
    }

    public void setKeyLocation(String keyLocation) {
        this.keyLocation = keyLocation;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }

    public int getAccessExpireDays() {
        return accessExpireDays;
    }

    public void setAccessExpireDays(int accessExpireDays) {
        this.accessExpireDays = accessExpireDays;
    }

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

    public int getRefreshExpireDays() {
        return refreshExpireDays;
    }

    public void setRefreshExpireDays(int refreshExpireDays) {
        this.refreshExpireDays = refreshExpireDays;
    }
}
