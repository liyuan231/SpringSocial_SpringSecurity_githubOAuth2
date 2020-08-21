package com.liyuan.github.autoconfiguration;

import com.liyuan.github.api.Github;
import com.liyuan.github.connect.GithubConnectionFactory;
import com.liyuan.social.SocialAutoConfigurerAdapter;
import com.liyuan.social.SocialWebAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

@Configuration
@ConditionalOnClass({SocialConfigurerAdapter.class, GithubConnectionFactory.class})
@ConditionalOnProperty(prefix = "spring.social.github", name = {"client-id", "client-secret"})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class GithubAutoConfiguration {
    @Configuration
    @EnableSocial
    @EnableConfigurationProperties(GithubProperties.class)
    @ConditionalOnWebApplication
    protected static class GithubConfigurerAdapter extends SocialAutoConfigurerAdapter {
        private final GithubProperties githubProperties;

        public GithubConfigurerAdapter(GithubProperties githubProperties) {
            this.githubProperties = githubProperties;
        }

//        @Value("${spring.social.github.redirect_uri}")
//        private String callbackUrl;
        protected ConnectionFactory<Github> createConnectionFactory() {
            GithubConnectionFactory factory = new GithubConnectionFactory(githubProperties.getClientId(), githubProperties.getClientSecret());
//            OAuth2Parameters parameters = new OAuth2Parameters();
//            parameters.setRedirectUri(callbackUrl);
            factory.setScope(githubProperties.getScope());
            return factory;
        }

    }
}
