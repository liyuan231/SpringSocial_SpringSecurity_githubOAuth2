package com.liyuan.wechat;

import com.liyuan.wechat.api.User;
import com.liyuan.wechat.api.Wechat;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

//@Component
public class WechatSignInAdapter implements SignInAdapter {
    @Override
    public String signIn(String openId, Connection<?> connection, NativeWebRequest request) {
        System.out.println("signIn is being invoked!");
        ConnectionKey key = connection.getKey();
        if ("wechat".equalsIgnoreCase(key.getProviderId())) { // 这里的if判断可对微信开放平台和微信公众平台帐号登录做不同的逻辑处理，此例子代码处理逻辑相同
            User user = ((Wechat) connection.getApi()).userOperations().getUserProfile(openId);// 默认语言为中文，如果想切换为中文可改为:getUserProfile(openId,WechatLangEnum.ZH_CN);
            System.out.println(user); // 打印微信用户详细信息
        }
        return "static/login.html";
    }
}
