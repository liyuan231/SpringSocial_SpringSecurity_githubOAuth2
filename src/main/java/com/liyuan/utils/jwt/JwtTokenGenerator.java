package com.liyuan.utils.jwt;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class JwtTokenGenerator {
    private static final String JWT_EXP_KEY = "expireAt";
    private JwtPayloadBuilder jwtPayloadBuilder = new JwtPayloadBuilder();
    private JwtProperties jwtProperties;
    private JwtTokenCacheStorage jwtTokenCacheStorage;
    private KeyPair keyPair;

    public JwtTokenGenerator(JwtTokenCacheStorage jwtTokenCacheStorage,
                             JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.jwtTokenCacheStorage = jwtTokenCacheStorage;
        KeyPairFactory keyPairFactory = new KeyPairFactory();
        this.keyPair = keyPairFactory.create(jwtProperties.getKeyLocation(),
                jwtProperties.getKeyAlias(),
                jwtProperties.getKeyPass());
    }

    public JwtTokenPair jwtTokenPair(String audience,
                                     Collection<GrantedAuthority> authorities,
                                     Map<String, String> additional) {
        String accessToken = jwtToken(audience, jwtProperties.getAccessExpireDays(), authorities, additional);
        String refreshToken = jwtToken(audience, jwtProperties.getRefreshExpireDays(), authorities, additional);
        JwtTokenPair jwtTokenPair = new JwtTokenPair();
        jwtTokenPair.setAccessToken(accessToken);
        jwtTokenPair.setRefreshToken(refreshToken);
        return jwtTokenPair;

    }

    private String jwtToken(String audience,
                            int expireDays,
                            Collection<GrantedAuthority> authorities,
                            Map<String, String> additional) {
        String payload = jwtPayloadBuilder
                .issuer(jwtProperties.getIssuer())
                .subscriber(jwtProperties.getSubscriber())
                .audience(audience)
                .authorities(authorities)
                .expireDays(expireDays)
                .builder();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RsaSigner signer = new RsaSigner(privateKey);
        return JwtHelper.encode(payload, signer).getEncoded();
    }

    public JSONObject decodeAndVerify(String jwtToken) throws InternalAuthenticationServiceException {
        Assert.hasText(jwtToken, "jwtToken must not be null!");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();
        SignatureVerifier signatureVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, signatureVerifier);
        String claims = jwt.getClaims();
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(claims);
        } catch (JSONException e) {
            throw new InternalAuthenticationServiceException("can not cast to JSONObject.");
        }
        System.out.println(jsonObject);
        String expireAt = jsonObject.getString(JWT_EXP_KEY);
        if (isExpire(expireAt)) {
            throw new IllegalStateException("jwt token is expired!");
        }
        return jsonObject;
    }

    private boolean isExpire(String expireAt) {
        return LocalDateTime.now().isAfter(LocalDateTime.parse(expireAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
