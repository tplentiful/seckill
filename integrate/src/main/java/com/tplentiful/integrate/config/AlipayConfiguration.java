package com.tplentiful.integrate.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class AlipayConfiguration {

    @Bean
    public AlipayClient alipayClient (AlipayProperties alipayProperties) {
        return  new DefaultAlipayClient(alipayProperties.getServerUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType());
    }



    @Data
    @Component
    @ConfigurationProperties(prefix = "alipay")
    public static class AlipayProperties {
        private String serverUrl;
        private String appId;
        private String privateKey;
        private String format;
        private String charset;
        private String alipayPublicKey;
        private String signType;
        private String returnUrl;
    }
}
