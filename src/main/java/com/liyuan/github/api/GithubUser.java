package com.liyuan.github.api;

import com.liyuan.model.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 */
public class GithubUser extends SysUser {
    /**
     * 用户在github的Id，虽然还有一个NODE_ID不知道是做什么的
     */
    private String providerUserId;
    /**
     * 用户头像
     */
    private String avatar_url;

    private String email;

    public GithubUser(String loginUsername,
                      String password, Collection<? extends GrantedAuthority> authorities,
                      String userId,
                      Integer roleId,
                      String providerUserId,
                      String avatar_url,
                      String email) {
        super(loginUsername, password, authorities, userId, roleId);
        this.providerUserId = providerUserId;
        this.avatar_url = avatar_url;
        this.email = email;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "providerUserId='" + providerUserId + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
