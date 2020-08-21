package com.liyuan.utils.jwt;

import java.util.Map;
import java.util.Set;

public class JwtUtil {
    private JwtPayloadBuilder jwtPayloadBuilder = new JwtPayloadBuilder();
    private JwtProperties jwtProperties;

    private String jwtToken(String audience,
                            String expireAt,
                            Set<String> roles,
                            Map<String, String> additional,
                            ) {
        String payload =jwtPayloadBuilder.issuer()
    }
}
