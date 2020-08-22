package com.liyuan;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.utils.jwt.JwtProperties;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import com.liyuan.utils.jwt.JwtTokenPair;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class test {
    @Test
    public void test1() {
        JwtTokenCacheStorage jwtTokenCacheStorage = new JwtTokenCacheStorage();
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setKeyLocation("liyuan.jks");
        jwtProperties.setKeyAlias("Liyuan123");
        jwtProperties.setKeyPass("123456");
        jwtProperties.setIssuer("Liyuan");
        jwtProperties.setSubscriber("all");
        jwtProperties.setAccessExpireDays(10);
        jwtProperties.setRefreshExpireDays(90);
        JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator(jwtTokenCacheStorage, jwtProperties);
        Set<String> roles = new HashSet<>();
        roles.add("a");
        roles.add("b");
        JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair("liyuan", roles, null);
        String accessToken = jwtTokenPair.getAccessToken();
        String refreshToken = jwtTokenPair.getRefreshToken();
        System.out.println("accessToken:" + accessToken);
        System.out.println("refreshToken:" + refreshToken);
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(accessToken);


    }
}
