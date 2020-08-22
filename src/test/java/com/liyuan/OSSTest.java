package com.liyuan;

import com.liyuan.utils.qcloud.QCloudCosUtils;
import com.liyuan.utils.qcloud.QCloudProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class OSSTest {
    @Test
    public void test1() {
        COSCredentials cosCredentials = new BasicCOSCredentials(
                "AKIDsjKkunCxe5SEl2w8H1ViNXS8gyzkqE6DLIYUAN",
                "zjmo3dxIOjdIkwwglbBXdVwaeRS7rLYjLIYUAN");
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        QCloudProperties qCloudProperties = new QCloudProperties();
        qCloudProperties.setBucketName("liyuan123-osstest-1300780430");
        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudProperties, cosClient);
//        qCloudCosUtils.delete("thumbnail/1.PNG");
        URL url = qCloudCosUtils.upload("test/1.PNG", new File("C:\\Users\\Administrator\\Desktop\\1.PNG"));
        System.out.println(url.toString());
        /**
         * 获取url链接，默认1小时过期
         * http://liyuan123-osstest-1300780430.cos.ap-guangzhou.myqcloud.com/test/1.PNG?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDsjKkunCxe5SEl2w8H1ViNXS8gyzkqE6D%26q-sign-time%3D1598088868%3B1598092468%26q-key-time%3D1598088868%3B1598092468%26q-header-list%3Dhost%26q-url-param-list%3D%26q-signature%3D5107ae96325d8447e2bdea7b06c6d5bb04cc4515&imageView2/1/w/300/h/200/rq!/10
         */
//        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(qCloudProperties.getBucketName(),"/test/pom.xml", HttpMethodName.GET);
//        URL url = cosClient.generatePresignedUrl(request);
//        System.out.println(url.toString());

        System.out.println(1);
//        cosClient.
    }
    @Test
    public void getPreUrlOfTheFile(){
    }
}
