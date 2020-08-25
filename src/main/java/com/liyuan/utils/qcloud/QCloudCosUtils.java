package com.liyuan.utils.qcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * 一直都在想如何创建文件夹，但其实根本没必要，
 * 获取文件时使用key-> "thumbnail/1.PNG"
 * 删除文件时也同上，根本没必要创建文件夹
 */
public class QCloudCosUtils {
    private QCloudProperties qCloudProperties;
    private COSClient cosClient;
    /**
     * 明确指定一个bucket即可，因为一个足以胜任所有工作。
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    public QCloudCosUtils(QCloudProperties qCloudProperties,
                          COSClient cosClient) {
        this.qCloudProperties = qCloudProperties;
        this.cosClient = cosClient;
    }

    /**
     * 按照我的规则，仅可使用一个存储桶
     *
     * @return
     */
    private List<Bucket> listBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();
        return buckets;
    }

    public void delete(String key) {
        try {
            cosClient.deleteObject(this.qCloudProperties.getBucketName(), key);
        } catch (CosClientException e) {
            logger.debug("delete operation failed!");
            throw e;
        }
    }

    public URL upload(String key, File file) {
        Assert.notNull(file, "file should not be null!");
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("The key should not be empty!");
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.qCloudProperties.getBucketName(), key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(qCloudProperties.getBucketName(), key, HttpMethodName.GET);
        URL url = cosClient.generatePresignedUrl(request);
        logger.debug("上传文件成功！");
        return url;
    }
    public URL upload(String key, InputStream inputStream) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("The key should not be empty!");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(inputStream.available());//此处需要限制缓冲的文件的大小，我这里不做限制，交给前端处理
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.qCloudProperties.getBucketName(),key,inputStream,objectMetadata);
        cosClient.putObject(putObjectRequest);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(qCloudProperties.getBucketName(), key, HttpMethodName.GET);
        URL url = cosClient.generatePresignedUrl(request);
        logger.debug("上传文件成功！");
        return url;
    }

    public ObjectListing listObjects(){
        return cosClient.listObjects(this.qCloudProperties.getBucketName());
    }


}
