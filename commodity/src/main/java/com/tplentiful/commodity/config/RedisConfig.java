package com.tplentiful.commodity.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new GenericFastJsonRedisSerializer());
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setStringSerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        template.setHashKeySerializer(new GenericFastJsonRedisSerializer());
        template.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }
}
