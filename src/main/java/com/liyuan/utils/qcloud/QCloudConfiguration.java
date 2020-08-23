package com.liyuan.utils.qcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableConfigurationProperties(QCloudProperties.class)
@ConditionalOnProperty(prefix = "qcloud.api", name = "enabled")
public class QCloudConfiguration {


    @Bean
    COSClient createCOSClient(QCloudProperties qCloudProperties) {
        COSCredentials cosCredentials = new BasicCOSCredentials(
                qCloudProperties.getSecretId(),
                qCloudProperties.getSecretKey());
        Region region = new Region(qCloudProperties.getRegionName());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cosCredentials, clientConfig);
    }

    @Bean
    QCloudCosUtils qCloudCosUtils(COSClient cosClient,
                                  QCloudProperties qCloudProperties) {
        QCloudCosUtils qCloudCosUtils = new QCloudCosUtils(qCloudProperties, cosClient);
        return qCloudCosUtils;
    }
}
