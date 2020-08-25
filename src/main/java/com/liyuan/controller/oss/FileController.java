package com.liyuan.controller.oss;

import com.liyuan.model.Rest;
import com.liyuan.model.RestBody;
import com.liyuan.service.OSSService;
import com.liyuan.utils.ImageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RestController
public class FileController {
    @Autowired
    OSSService ossService;

    /**
     * 默认文件大小需要小于1MB,需由前端处理，后端直接会报错，无法处理
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Rest upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file==null||file.isEmpty()) {
            return RestBody.failure(-1, "上传文件不能为空！");
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (!StringUtils.hasText(suffix)) {
            return RestBody.failure(-2, "上传文件格式不能为空！");
        }
        /**
         * 若判断格式为图片，需要在公共存储区中在放置一份缩略图
         */
        if(ImageTypeEnum.isImageType(suffix)){

        }
        System.out.println(filename);
        InputStream inputStream = file.getInputStream();
        URL uploadUrl = ossService.upload(filename, inputStream);
        System.out.println(uploadUrl.toString());
        return null;
    }
}
