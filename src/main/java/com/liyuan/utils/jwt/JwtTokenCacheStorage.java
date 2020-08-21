package com.liyuan.utils.jwt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class JwtTokenCacheStorage {
    private static final String TOKEN_CACHE = "usrTkn";
    @CachePut(value = TOKEN_CACHE,key = "#userId")
    public JwtTokenPair put(JwtTokenPair jwtTokenPair,
                            String userId){
        return jwtTokenPair;
    }
    @CacheEvict(value = TOKEN_CACHE,key = "#userId")
    public void expire(String userId){

    }
    @Cacheable(value = TOKEN_CACHE,key = "#userId")
    public JwtTokenPair get(String userId){return null;}
}
