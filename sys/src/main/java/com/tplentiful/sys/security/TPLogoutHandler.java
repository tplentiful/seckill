package com.tplentiful.sys.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tplentiful.common.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TPLogoutHandler implements LogoutHandler {

    private StringRedisTemplate stringRedisTemplate;

    public TPLogoutHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        JSONObject principal = JSON.parseObject(JSON.toJSONString(authentication.getPrincipal()));
        long id = Long.parseLong(principal.get("id") + "");
        stringRedisTemplate.opsForValue().setBit(RedisConstant.STATISTICS_ONLINE_COUNT, id, false);
    }
}
