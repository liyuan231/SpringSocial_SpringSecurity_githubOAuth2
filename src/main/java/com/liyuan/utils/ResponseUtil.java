package com.liyuan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyuan.model.Rest;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void responseJsonWriter(HttpServletResponse response, Rest rest) throws IOException {
        if (response.isCommitted()) {
            System.out.println("response has been commited!(com.liyuan.utils.ResponseUtil.responseJsonWriter)");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resposneBody = objectMapper.writeValueAsString(rest);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resposneBody);
        printWriter.flush();
        printWriter.close();

    }

}
