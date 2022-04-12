package com.tplentiful.sys.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.common.constant.SecurityConstant;
import com.tplentiful.common.utils.TR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TPAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private StringRedisTemplate stringRedisTemplate;

    public TPAuthenticationSuccessHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        JSONObject principal = JSON.parseObject(JSON.toJSONString(authentication.getPrincipal()));
        long id = Long.parseLong(principal.get("id") + "");
        String username = (String) principal.get("username");
        String token = TPJwtUtil.create(id, username, authorities);
        // 登录计数逻辑
        stringRedisTemplate.opsForValue().setBit(RedisConstant.STATISTICS_LOGIN_COUNT, id, true);
        stringRedisTemplate.opsForValue().setBit(RedisConstant.STATISTICS_ONLINE_COUNT, id, true);
        response.setHeader(SecurityConstant.TOKEN_KEY, token);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(JSON.toJSONString(TR.ok("登录成功")));

    }

}
