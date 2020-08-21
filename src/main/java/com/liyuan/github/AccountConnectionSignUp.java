package com.liyuan.github;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * TODO 可于此处在本地用户表中插入一个新用户然后在返回userId
 */
public class AccountConnectionSignUp implements ConnectionSignUp {

    private final JdbcTemplate jdbcTemplate;

    public AccountConnectionSignUp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String execute(Connection<?> connection) {
        //TODO 此处没什么思路,目前将时间戳作为userId
        connection.getKey().getProviderUserId();
        long l = System.currentTimeMillis();
        return String.valueOf(l);
    }
}
