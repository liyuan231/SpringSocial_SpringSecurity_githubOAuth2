package com.liyuan.config;

import com.liyuan.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ColumnMapRowMapper columnMapRowMapper;

    /**
     * TODO 普通用户登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("普通登录：" + username);
        return null;
    }


    /**
     *
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        System.out.println("第三方登录：" + userId);
//        throw new UsernameNotFoundException("test");
        //TODO 这里有待测试
        List<Map<String, Object>> query = jdbcTemplate.query("select * from sysuser where userId=?", columnMapRowMapper, userId);
        if (query.size() == 0) {
            throw new UsernameNotFoundException("用户名找不到！请先注册！");
        }
        Map<String, Object> result = null;
        result = query.get(0);
        Integer roleId = (Integer) result.get("roleId");
        List<String> authorities = jdbcTemplate.queryForList("select authorityDetail from authority where roleId = ?",String.class, roleId);
        Collection<GrantedAuthority> authoritiesCollections = new ArrayList<>();
        for (String grantedAuthority : authorities) {
            authoritiesCollections.add((GrantedAuthority) () -> grantedAuthority);
        }
        return new SysUser("[USERNAME]", "[PASSWORD]", true, true, true, true, authoritiesCollections, userId, roleId);
    }
}
