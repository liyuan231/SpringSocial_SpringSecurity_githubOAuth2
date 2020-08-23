package com.liyuan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuan.utils.jwt.JwtProperties;
import com.liyuan.utils.jwt.JwtTokenCacheStorage;
import com.liyuan.utils.jwt.JwtTokenGenerator;
import com.liyuan.utils.jwt.JwtTokenPair;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommonTest {
    private String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWRpZW5jZSI6ImxpeXVhbiIsInN1YnNjcmliZXIiOiJhbGwiLCJpc3N1ZUF0IjoiMjAyMC0wOC0yMyAxMDo0NTozNCIsImV4cGlyZUF0IjoiMjAyMC0wOS0wMiAxMDo0NTozNCIsImlzc3VlciI6IkxpeXVhbiIsImp0aSI6IjE1OTgxNTA3MzQzMzIiLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiYXV0aG9yaXR5MSJ9LHsiYXV0aG9yaXR5IjoiYXV0aG9yaXR5MiJ9XX0.k-Ex5IWBverbKXg3cnOI1Uf4rAZOJdwI3XM_o0aChc4evbWOQtPP9qKnU8dzOSeFqfj85Lj4D8-U-uAXvdc826c_IusG0PzQsPqbTDHqbqDr359K9Nhu4a2h5IOvQd3mR89kYnqhXnCErp_OfJLFejE-KyjXA-9yXC6KwHXX8OQ";
    private JwtTokenGenerator jwtTokenGenerator;

    @Before
    public void before() {
        JwtTokenCacheStorage jwtTokenCacheStorage = new JwtTokenCacheStorage();
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setKeyLocation("liyuan.jks");
        jwtProperties.setKeyAlias("Liyuan123");
        jwtProperties.setKeyPass("123456");
        jwtProperties.setIssuer("Liyuan");
        jwtProperties.setSubscriber("all");
        jwtProperties.setAccessExpireDays(10);
        jwtProperties.setRefreshExpireDays(90);
        jwtTokenGenerator = new JwtTokenGenerator(jwtTokenCacheStorage, jwtProperties);
    }

    @Test
    public void test1() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> "authority1");
        authorities.add((GrantedAuthority) () -> "authority2");
//        List<String> authorities = new ArrayList<>();
//        authorities.add("test1");
//        authorities.add("test2");
        JwtTokenPair jwtTokenPair = jwtTokenGenerator.jwtTokenPair("liyuan", authorities, null);
        String accessToken = jwtTokenPair.getAccessToken();
        String refreshToken = jwtTokenPair.getRefreshToken();
        System.out.println("accessToken:" + accessToken);
        System.out.println("refreshToken:" + refreshToken);
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(accessToken);
    }

    @Test
    public void test2() {
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(access_token);
        JSONArray authorities = jsonObject.getJSONArray("authorities");
        Iterator<Object> iterator = authorities.iterator();
        Collection<GrantedAuthority> authorities1 = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            authorities1.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return next.getString("authority");
                }
            });
        }
        for(GrantedAuthority authority:authorities1){
            System.out.println(authority.getAuthority());
        }

    }

}
