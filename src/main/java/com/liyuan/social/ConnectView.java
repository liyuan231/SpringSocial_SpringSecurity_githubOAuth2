package com.liyuan.social;

import com.liyuan.model.RestBody;
import com.liyuan.utils.ResponseUtil;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        ResponseUtil.responseJsonWriter(response, RestBody.okData(map));
    }
}
