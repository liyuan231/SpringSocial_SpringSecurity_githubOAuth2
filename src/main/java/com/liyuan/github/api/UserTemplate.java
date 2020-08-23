package com.liyuan.github.api;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.github.UrlConstants;
import com.liyuan.model.RoleType;
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
     * snsapi_userinfo	/sns/userinfo	获取用户个第三方github的人信息,
     * TODO 此处获取的用户的一些信息会设置到connection处，因此无需在此处插入新用户
     * 通过openid以及access_token及lang
     *
     * @param
     * @return
     */
    @Override
    public GithubUser getUserProfile() {
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
        String providerUserId = String.valueOf(userInfoMap.get("id"));
        String username = (String) userInfoMap.get("login");
        String avatarUrl = (String) userInfoMap.get("avatar_url");
        String email = (String) userInfoMap.get("email");
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add((GrantedAuthority) () -> "test");
        //从第三方获取到了用户信息，先这样做
        GithubUser githubUser = new GithubUser(username, "", authorities,"[USERID]",
                RoleType.ORDINARY.value(), providerUserId,avatarUrl,email);
        System.out.println("获取Github用户成功！" + githubUser.toString());
        return githubUser;
    }
}
