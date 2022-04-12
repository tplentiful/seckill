package com.tplentiful.sys.security;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TPCheckCodeAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationProvider authenticationProvider;

    private StringRedisTemplate stringRedisTemplate;

    public TPCheckCodeAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
    }

    public TPCheckCodeAuthenticationProcessingFilter(AuthenticationProvider provider, String defaultFilterProcessesUrl, StringRedisTemplate stringRedisTemplate) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        super.setAuthenticationSuccessHandler(new TPAuthenticationSuccessHandler(stringRedisTemplate));
        super.setAuthenticationFailureHandler(new TPAuthenticationFailureHandler());
        this.authenticationProvider = provider;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        JSONObject reqBody = JSON.parseObject(IoUtil.read(request.getInputStream(), StandardCharsets.UTF_8));
        String email = "";
        String checkCode = "";
        Object tempEmail = reqBody.get("email");
        Object tempCheckCode = reqBody.get("checkCode");

        if (tempCheckCode != null) {
            checkCode = (String) tempCheckCode;
        }

        if (tempEmail != null) {
            email = (String) tempEmail;
        }
        email = email.trim();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, checkCode);
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.authenticationProvider.authenticate(token);
    }
}
