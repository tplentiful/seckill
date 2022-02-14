package com.tplentiful.integrate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class QQConfiguration {

    @Data
    @Component
    @ConfigurationProperties(prefix = "qq")
    public static class QQProperties {
        private String authUrl;
        private String responseType;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String display;
        private String tokenUrl;
        private String fmt;
        private String openIdURL;
    }
}
