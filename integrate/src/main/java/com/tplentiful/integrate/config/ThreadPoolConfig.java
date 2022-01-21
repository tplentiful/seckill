package com.tplentiful.integrate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class ThreadPoolConfig {


    @Bean("serviceExecutor")
    public ThreadPoolExecutor serviceExecutor(ServiceExecutorProperties serviceExecutorProperties) {
        return new ThreadPoolExecutor(
                serviceExecutorProperties.corePoolSize,
                serviceExecutorProperties.maximumPoolSize,
                serviceExecutorProperties.keepAliveTime,
                serviceExecutorProperties.unit,
                new LinkedBlockingDeque<>(1000),
                r -> new Thread(r, "任务执行线程池 " + serviceExecutorProperties.count.getAndIncrement() + " 支"),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    @Data
    @Component
    @ConfigurationProperties(prefix = "service-executor")
    public static class ServiceExecutorProperties {
        private AtomicInteger count = new AtomicInteger(1);
        private Integer corePoolSize;
        private Integer maximumPoolSize;
        private Long keepAliveTime;
        private TimeUnit unit;
    }
}
