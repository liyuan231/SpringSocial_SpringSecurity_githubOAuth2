package com.liyuan.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("普通登录：" + username);
        return null;
    }

    /**
     * TODO 此处用于验证第三方用户是否在本地用户表中有数据，若有直接返回，我这里暂时不设置本地用户表以及角色权限表，之后再改
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        System.out.println("第三方登录：" + userId);
//        throw new UsernameNotFoundException("test");
        return new SocialUser(userId, "password",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("test"));
    }
}
