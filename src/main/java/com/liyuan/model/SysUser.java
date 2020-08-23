package com.liyuan.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

public class SysUser extends SocialUser {

    /**
     * 用户在本系统中的唯一标志
     */
    private String userId;
    private Integer roleId;


    public SysUser(String username,
                   String password, Collection<? extends GrantedAuthority> authorities,
                   String userId,
                   Integer roleId) {
        super(username, password, authorities);
        this.userId = userId;
        this.roleId = roleId;
    }

    public SysUser(String username,
                   String password,
                   boolean enabled,
                   boolean accountNonExpired,
                   boolean credentialsNonExpired,
                   boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities,
                   String userId,
                   Integer roleId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
