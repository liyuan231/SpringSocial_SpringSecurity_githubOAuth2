package com.liyuan.controller.oss;

import com.alibaba.fastjson.JSONObject;
import com.liyuan.model.Rest;
import com.liyuan.model.RestBody;
import com.liyuan.model.TunedCOSObject;
import com.liyuan.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OSSController {
    OSSService ossService;
    @Autowired
    public void setOssService(OSSService ossService) {
        this.ossService = ossService;
    }

    @GetMapping("/listObjects")
    public Rest listObjects() {
        List<TunedCOSObject> tunedCOSObjects = ossService.listObjects();
        return RestBody.okData(tunedCOSObjects,"获取bucket列表成功！");
    }
    @GetMapping("/delete")
    public Rest delete(@RequestParam("key")String key){
        ossService.delete(key);
        return RestBody.ok("删除成功！");
    }

}
