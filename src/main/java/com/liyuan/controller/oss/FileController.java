package com.liyuan.controller.oss;

import com.liyuan.model.Rest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileController {
    @RequestMapping("/upload")
    public Rest upload(HttpServletRequest request) {
//        if(file.isEmpty()){
//            return  RestBody.failure(404,"file is empty!");
//        }
//        String fileName = file.getOriginalFilename();
        return null;
    }
}
