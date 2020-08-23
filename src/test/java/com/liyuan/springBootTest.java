package com.liyuan;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.model.SysUser;
import com.liyuan.utils.jwt.JwtProperties;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import com.liyuan.utils.jwt.JwtTokenPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.MappingSqlQueryWithParameters;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class springBootTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void test2() {
        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> query = jdbcTemplate.query("select * from sysuser where userId=?;", rowMapper, "1598104960059");
        Map<String, Object> result = null;
        if (query.size() >= 1) {
            result = query.get(0);
        }
        Integer roleId = (Integer) result.get("roleId");
        List<String> authorities = jdbcTemplate.queryForList("select authorityDetail from authority where roleId = ?",String.class, roleId);
        for(String s:authorities){
            System.out.println(s);
        }
//        Integer roleId = sysUser.getRoleId();
//        List<String> authorities = jdbcTemplate.queryForList("select * from authority where roleId = ?", String.class, roleId);
//        Collection<GrantedAuthority> authoritiesCollections = new ArrayList<>();
//        for (String grantedAuthority : authorities) {
//            authoritiesCollections.add((GrantedAuthority) () -> grantedAuthority);
//        }
    }

}
