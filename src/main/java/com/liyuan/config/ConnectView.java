package com.liyuan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuan.model.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ConnectView extends AbstractView {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        ResultJson<Object> resultJson = new ResultJson<>(200, "success", map);
        response.getWriter().write(objectMapper.writeValueAsString(resultJson));
    }
}
