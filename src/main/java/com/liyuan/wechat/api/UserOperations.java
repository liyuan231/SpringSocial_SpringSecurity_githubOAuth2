package com.liyuan.wechat.api;

import com.liyuan.wechat.WechatLangEnum;

public interface UserOperations {
    User getUserProfile(String openid);
    User getUserProfile(String openId, WechatLangEnum wechatLangEnum);
}
