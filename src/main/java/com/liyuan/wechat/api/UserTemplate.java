package com.liyuan.wechat.api;

import com.liyuan.wechat.UrlConstants;
import com.liyuan.wechat.WechatLangEnum;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

/**
 * 通过用户access_token以及openid获取用户信息
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
     * @param wechatLangEnum
     * @return
     */
    @Override
    public User getUserProfile(String openId, WechatLangEnum wechatLangEnum) {
        Assert.notNull(openId, "opneId should not be null!");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>(3);
        map.add("openId", openId);
        map.add("lang", wechatLangEnum.getValue());
        map.add("access_token", accessToken);
        return restOperations.postForObject(UrlConstants.USERINFO_API_URL, map, User.class);
    }

    @Override
    public User getUserProfile(String openId) {
        return getUserProfile(openId, WechatLangEnum.ZH_CN);
    }
}
