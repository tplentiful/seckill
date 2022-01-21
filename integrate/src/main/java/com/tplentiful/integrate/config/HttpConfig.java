package com.tplentiful.integrate.config;

import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class HttpConfig {

    @Bean
    public OkHttpClient okHttpClient(OkHttpProperties properties) {
        return new OkHttpClient()
                .newBuilder()
                .callTimeout(Duration.ofMinutes(properties.callTimeout))
                .connectTimeout(Duration.ofMinutes(properties.connectTimeout))
                .readTimeout(Duration.ofMinutes(properties.readTimeout))
                .writeTimeout(Duration.ofMinutes(properties.writeTimeout))
                .build();
    }



    @Data
    @Component
    @ConfigurationProperties(prefix = "okhttp")
    public static class OkHttpProperties {
        private Long callTimeout;
        private Long connectTimeout;
        private Long readTimeout;
        private Long writeTimeout;
    }
}
