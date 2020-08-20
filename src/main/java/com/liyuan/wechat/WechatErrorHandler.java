package com.liyuan.wechat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * 定制的关于微信的错误处理器
 */
public class WechatErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(HttpStatus.Series.CLIENT_ERROR.equals(response.getStatusCode().series())){
            Map<String,Object> errorDetails = extractErrorDetailsFromResponse(response);
            throw new UncategorizedApiException("wechat",errorDetails.containsKey("errmeg")?
                    (String) errorDetails.get("errmsg"):"Unkonwn error",null);

        }
        handleUncategorizedError(response);
    }

    private Map<String, Object> extractErrorDetailsFromResponse(ClientHttpResponse response) throws IOException {
        try {
            return new ObjectMapper(new JsonFactory()).readValue(response.getBody(),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (JsonParseException e) {
            return Collections.emptyMap();
        }
    }

    private void handleUncategorizedError(ClientHttpResponse response) {
        try {
            super.handleError(response);
        } catch (IOException e) {
//            e.printStackTrace();
            throw new UncategorizedApiException("wechat", "Error consuming wechat REST api", e);
        }
    }
}
