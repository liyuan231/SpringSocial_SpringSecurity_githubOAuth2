package com.liyuan.github;

import com.liyuan.model.RoleType;
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

    /**
     * TODO 此处需要设计github账号表
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        //用户在github上的账号
        String providerUserId = connection.getKey().getProviderUserId();
        //将当前时间戳作为用户的账号,既然userconnection处已经有了第三方的账号了，那我何必在重新写里面的信息？？
        String userId = String.valueOf(System.currentTimeMillis());
        //插入githubUser表一个用户
        jdbcTemplate.update("insert into sysuser(userId,roleId) values(?,?)",userId, RoleType.ORDINARY.value());
        return userId;
    }
}
