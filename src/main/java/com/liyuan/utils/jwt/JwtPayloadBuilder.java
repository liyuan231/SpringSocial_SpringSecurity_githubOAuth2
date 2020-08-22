package com.liyuan.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JwtPayloadBuilder {
    private Map<String, String> payload = new HashMap<>();
    /**
     * 附加的属性
     */
    private Map<String, String> additional;
    /**
     * jwt签发者
     **/
    private String issuer;
    /**
     * jwt所面向的用户
     **/
    private String subscriber;
    /**
     * 接收jwt的一方
     **/
    private String audience;
    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     **/
    private LocalDateTime expireAt;
    /**
     * jwt的签发时间
     **/
    private LocalDateTime issueAt = LocalDateTime.now();
    /**
     * 权限集
     */
    private Set<String> roles = new HashSet<>();
    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     **/
    private String jti = String.valueOf(System.currentTimeMillis());

    public JwtPayloadBuilder issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }


    public JwtPayloadBuilder subscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public JwtPayloadBuilder audience(String audience) {
        this.audience = audience;
        return this;
    }


    public JwtPayloadBuilder roles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    public JwtPayloadBuilder expireDays(int days) {
        Assert.isTrue(days > 0, "jwt expireDate must after now");
        this.expireAt = this.issueAt.plusDays(days);
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public String builder() {
        payload.put("issuer", this.issuer);
        payload.put("subscriber", this.subscriber);
        payload.put("audience", this.audience);
        payload.put("expireAt", this.expireAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put("issueAt", this.issueAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put("jti", this.jti);

        if (!CollectionUtils.isEmpty(additional)) {
            payload.putAll(additional);
        }
        payload.put("roles", JSONObject.toJSONString(this.roles));
        return JSONObject.toJSONString(payload);
    }
}
