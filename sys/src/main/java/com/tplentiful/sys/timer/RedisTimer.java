package com.tplentiful.sys.timer;

import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.tplentiful.sys.service.LoginStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service
public class RedisTimer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void updateLoginCount() {
        log.info("执行登录人数统计");
        Long count = stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(RedisConstant.STATISTICS_LOGIN_COUNT.getBytes(StandardCharsets.UTF_8)));
        if (!ObjectUtils.isEmpty(count)) {
            LoginStatistics loginStatistics = new LoginStatistics();
            loginStatistics.setCount(count);
            loginStatistics.setCreateAt(new Date());
            loginStatistics.setUpdateAt(new Date());
            loginStatisticsService.save(loginStatistics);
            stringRedisTemplate.delete(RedisConstant.STATISTICS_LOGIN_COUNT);
        }
        log.info("登录人数： {}", count);
    }
}
