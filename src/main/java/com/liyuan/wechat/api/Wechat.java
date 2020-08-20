package com.liyuan.wechat.api;

import org.springframework.web.client.RestOperations;

public interface Wechat {
    /*
    获取用户信息
     */
    UserOperations userOperations();

    RestOperations restOperations();
}
