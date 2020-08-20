package com.liyuan.github;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class AccountConnectionSignUp implements ConnectionSignUp {

    private final JdbcTemplate jdbcTemplate;

    public AccountConnectionSignUp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String execute(Connection<?> connection) {
        //TODO 此处没什么思路
        connection.getKey().getProviderUserId();
        long l = System.currentTimeMillis();
        return String.valueOf(l);
    }
}
