package com.liyuan;

import com.liyuan.utils.qcloud.QCloudCosUtils;
import com.liyuan.utils.qcloud.QCloudProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.region.Region;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

public class OSSTest {
    private QCloudCosUtils qCloudCosUtils;
    private COSClient cosClient;
    private QCloudProperties qCloudProperties;

    @Before
    public void before() {
        COSCredentials cosCredentials = new BasicCOSCredentials(
                "AKIDsjKkunCxe5SEl2w8H1ViNXS8gyzkqE6D",
                "zjmo3dxIOjdIkwwglbBXdVwaeRS7rLYj");
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        cosClient = new COSClient(cosCredentials, clientConfig);
        qCloudProperties = new QCloudProperties();
        qCloudProperties.setBucketName("liyuan123-osstest-1300780430");
        qCloudCosUtils = new QCloudCosUtils(qCloudProperties, cosClient);
    }

    @Test
    public void test1() {

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
    public void getPreUrlOfTheFile() {
    }

    @Test
    public void test2() {
        ObjectListing objectListing = cosClient.listObjects(this.qCloudProperties.getBucketName());
        List<COSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for(COSObjectSummary cosObjectSummary:objectSummaries){
            //文件所在位置（有用）
            String key = cosObjectSummary.getKey();
            String etag = cosObjectSummary.getETag();
            //以字节为单位（有用）
            long fileSize = cosObjectSummary.getSize();
            //文件存储的类型，无用
            String storageClass = cosObjectSummary.getStorageClass();
            System.out.println(key+":"+etag+":"+fileSize+":"+storageClass);
        }
    }
}
