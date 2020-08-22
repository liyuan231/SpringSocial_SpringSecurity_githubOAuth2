package com.liyuan.github.api;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.github.UrlConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestOperations;

import java.util.*;

/**
 * TODO 获取用户信息
 */
public class UserTemplate implements UserOperations {
    public UserTemplate(RestOperations restOperations, String accessToken) {
        this.restOperations = restOperations;
        this.accessToken = accessToken;
    }

    private RestOperations restOperations;
    private String accessToken;

    /**
     * snsapi_userinfo	/sns/userinfo	获取用户个人信息
     * 通过openid以及access_token及lang
     *
     * @param openId
     * @return
     */
    @Override
    public User getUserProfile(String openId) {
        System.out.println(accessToken);
        Map<String, String> map = new HashMap<>(3);
        map.put("access_token", accessToken);
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=utf-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        System.out.println(JSONObject.toJSONString(map));
        HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(map), headers);
        Map userInfoMap = restOperations.postForObject(UrlConstants.USERINFO_API_URL, formEntity, Map.class);
        assert userInfoMap != null;
        String userId = String.valueOf(userInfoMap.get("id"));
        String username = (String) userInfoMap.get("login");
        String avatarUrl = (String) userInfoMap.get("avatar_url");
        String email = (String) userInfoMap.get("email");
        //TODO 此处权限应当从数据库中获取
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add((GrantedAuthority) () -> "test");
        User user = new User(username, "", authorities, userId, username, avatarUrl, email);
        System.out.println("获取Github用户成功！" + user.toString());
        return user;
    }
}
