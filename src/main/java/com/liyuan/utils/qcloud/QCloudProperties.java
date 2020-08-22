package com.liyuan.utils.qcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "qcloud.api")
@Validated
public class QCloudProperties {
    private String secretId;
    private String secretKey;
    /**
     * 用一个存储桶就可以了，因为在这个桶里面也可以新建文件夹
     */
    private String bucketName;
    private String regionName;
    private String appId;

    @Override
    public String toString() {
        return "QCloudProperties{" +
                "secretId='" + secretId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        if (!StringUtils.hasText(secretId)) {
            throw new IllegalArgumentException("The secretId should not be empty!");
        }
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        if (!StringUtils.hasText(secretKey)) {
            throw new IllegalArgumentException("The secretKey should not be empty!");
        }
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        if (!StringUtils.hasText(bucketName)) {
            throw new IllegalArgumentException("The bucketName should not be empty!(因为一个bucket已经够用了，这样也比较整洁~)");
        }
        this.bucketName = bucketName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        if (!StringUtils.hasText(secretId)) {
            throw new IllegalArgumentException("The regionName should not be empty!");
        }
        this.regionName = regionName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
