package com.tplentiful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class CommodityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommodityApplication.class, args);
    }
}


