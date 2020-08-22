package com.liyuan.utils.qcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QCloudProperties.class)
@ConditionalOnProperty(prefix = "qcloud.config", name = "enabled")
public class QCloudConfiguration {

    private QCloudProperties qCloudProperties;

    @Bean
    COSClient createCOSClient() {
        COSCredentials cosCredentials = new BasicCOSCredentials(
                this.qCloudProperties.getSecretId(),
                this.qCloudProperties.getSecretKey());
        Region region = new Region(this.qCloudProperties.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cosCredentials, clientConfig);
    }

    @Bean
    QCloudCosUtils qCloudCosUtils(COSClient cosClient) {
        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudProperties, cosClient);
        return qCloudCosUtils;
    }
}
