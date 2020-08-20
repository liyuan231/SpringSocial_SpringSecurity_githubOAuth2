package com.liyuan.wechat.autoconfiguration;

import com.liyuan.social.SocialAutoConfigurerAdapter;
import com.liyuan.social.SocialWebAutoConfiguration;
import com.liyuan.wechat.api.Wechat;
import com.liyuan.wechat.connect.WechatConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class,WechatConnectionFactory.class})
@ConditionalOnProperty(prefix = "spring.social.wechat",name = {"app-id","app-secret"})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WechatAutoConfiguration {

    @Configuration
    @EnableSocial
    @EnableConfigurationProperties(WechatProperties.class)
    @ConditionalOnWebApplication
    @ConditionalOnProperty(prefix = "spring.social.wechat",name = {"app-id","app-secret"})
    protected static class WechatConfigurerAdapter extends SocialAutoConfigurerAdapter {
        private final WechatProperties wechatProperties;

        public WechatConfigurerAdapter(WechatProperties wechatProperties) {
            this.wechatProperties = wechatProperties;
        }
        protected ConnectionFactory<Wechat> createConnectionFactory() {
            System.out.println("org.springframework.social.connect.ConnectionFactory");
            final WechatConnectionFactory factory = new WechatConnectionFactory(wechatProperties.getAppId(),
                    wechatProperties.getAppSecret());
            factory.setScope(wechatProperties.getScope());
            return factory;
        }
    }
}
