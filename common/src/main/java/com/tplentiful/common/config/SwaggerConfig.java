package com.tplentiful.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Data
    @Component
    @ConfigurationProperties(prefix = "swagger")
    public static class SwaggerProperties {
        private Boolean enable;
        private String applicationName;
        private String applicationDesc;
        private String applicationVersion;
    }



    @Bean
    public Docket docket(SwaggerProperties properties) {
        return new Docket(DocumentationType.OAS_30)
                .enable(properties.enable)
                .apiInfo(apiInfo(properties))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    /**
     * ApiInfo：主要返回接口和接口创建者的信息
     */
    private ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.applicationName)
                .description(properties.applicationDesc)
                .contact(new Contact("tplentiful", "https://tplentiful.bio", "tplentiful@163.com"))
                .version(properties.applicationVersion)
                .build();
    }
}
