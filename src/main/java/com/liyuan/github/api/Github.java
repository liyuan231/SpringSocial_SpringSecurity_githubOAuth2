package com.liyuan.github.api;

import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestOperations;

public interface Github extends ApiBinding {
    UserOperations userOperations();

    RestOperations restOperations();

}
