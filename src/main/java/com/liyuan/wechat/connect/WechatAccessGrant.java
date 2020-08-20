package com.liyuan.wechat.connect;

import org.springframework.social.oauth2.AccessGrant;

public class WechatAccessGrant extends AccessGrant {
    private String openid;
    private String unionid;
    public WechatAccessGrant(String accessToken,
                             String scope,
                             String refreshToken,
                             Long expiresIn,
                             String openId,
                             String unionId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openId;
        this.unionid = unionId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
