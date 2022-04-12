package com.tplentiful.sys.security;

import com.alibaba.fastjson.JSON;
import com.tplentiful.common.enums.ResCode;
import com.tplentiful.common.utils.TR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TPAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("认证失败: 返回消息认证错误！", exception);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        PrintWriter writer = response.getWriter();
        if (exception instanceof UsernameNotFoundException) {
            writer.write(JSON.toJSONString(new TR<Void>(ResCode.NO_USER.getCode(), exception.getMessage())));
        } else if (exception instanceof BadCredentialsException) {
            writer.write(JSON.toJSONString(new TR<Void>(ResCode.NO_PASSWD.getCode(), exception.getMessage())));
        } else {
            writer.write(JSON.toJSONString(TR.fail(exception.getMessage())));
        }
    }
}
