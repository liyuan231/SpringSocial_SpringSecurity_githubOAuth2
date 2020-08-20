package com.liyuan.github.api;

/**
 *
 */
public class User {
    /**
     * 用户Id，虽然还有一个NODE_ID不知道是做什么的
     */
    private String id;
    /**
     * 相当于登录用户名!
     */
    private String login;
    /**
     * 用户头像
     */
    private String avatar_url;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
