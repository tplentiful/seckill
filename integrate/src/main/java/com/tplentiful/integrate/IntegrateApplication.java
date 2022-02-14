package com.tplentiful.integrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.tplentiful")
public class IntegrateApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegrateApplication.class, args);
    }

}
