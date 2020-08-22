package com.liyuan.github.api;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

/**
 *
 */
public class User extends SocialUser {
    /**
     * 用户Id，虽然还有一个NODE_ID不知道是做什么的
     */
    private String userId;
    /**
     * 相当于登录用户名!
     */
    private String login;
    /**
     * 用户头像
     */
    private String avatar_url;

    private String email;

    public User(String username,
                String password,
                boolean enabled,
                boolean accountNonExpired,
                boolean credentialsNonExpired,
                boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities,
                String userId,
                String login,
                String avatar_url,
                String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.login = login;
        this.avatar_url = avatar_url;
        this.email = email;
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities, String userId, String login, String avatar_url, String email) {
        super(username, password, authorities);
        this.userId = userId;
        this.login = login;
        this.avatar_url = avatar_url;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
