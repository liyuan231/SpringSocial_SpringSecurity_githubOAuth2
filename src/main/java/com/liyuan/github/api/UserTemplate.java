package com.liyuan.github.api;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.github.UrlConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

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

        User user = new User();
        user.setId(String.valueOf(userInfoMap.get("id")));
        user.setLogin((String) userInfoMap.get("login"));
        user.setAvatar_url((String) userInfoMap.get("avatar_url"));
        user.setEmail((String) userInfoMap.get("email"));
        System.out.println("获取Github用户成功！" + user.toString());
        return user;
    }
}
