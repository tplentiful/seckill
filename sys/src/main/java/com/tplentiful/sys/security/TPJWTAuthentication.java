package com.tplentiful.sys.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tplentiful.common.constant.SecurityConstant;
import com.tplentiful.common.enums.ResCode;
import com.tplentiful.common.utils.TR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TPJWTAuthentication extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 JWT 相关信息
        String token = ServletUtil.getHeader(request, SecurityConstant.TOKEN_KEY, CharsetUtil.CHARSET_UTF_8);
        if (StringUtils.hasText(token)) {
            Map<String, Object> payload = TPJwtUtil.getPayload(token);
            log.info("{}", payload.get(TPJwtUtil.PAYLOAD_EXP));
            DateTime exp = DateUtil.date(Integer.toUnsignedLong((Integer) payload.get(TPJwtUtil.PAYLOAD_EXP)) * 1000);
            if (exp.isBefore(DateTime.now())) {
                log.error("当前令牌过期");
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(new TR<Void>(ResCode.EXP.getCode(), "登录信息过期，请重新登录")));
                return;
            }
            List<String> roles = JSON.parseObject(JSON.toJSONString(payload.get(TPJwtUtil.PAYLOAD_ROLES)), new TypeReference<List<String>>() {
            });
            List<GrantedAuthority> authorities = new LinkedList<>();
            for (String role : roles) {
                if (role.startsWith("ROLE_"))
                    authorities.add(new SimpleGrantedAuthority(role));
                else
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            TPUser tpUser = new TPUser();
            tpUser.setId(Long.parseLong(payload.get(TPJwtUtil.PAYLOAD_ID) + ""));
            tpUser.setUsername((String) payload.get(TPJwtUtil.PAYLOAD_EMAIL));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tpUser, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);

    }
}
