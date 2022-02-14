package com.tplentiful.sys.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.tplentiful.common.constant.StringConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class TpJwtUtil {
    public static final String PAYLOAD_EMAIL = "email";
    public static final String PAYLOAD_ROLES = "roles";
    public static final byte[] KEY = "tplentiful".getBytes(StandardCharsets.UTF_8);
    public static final Integer EXPIRES = 6;
    public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);

    public static String create(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> map = new HashMap<>();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        map.put(PAYLOAD_ROLES, roles);
        map.put(PAYLOAD_EMAIL, username);
        return JWT.create()
                .addPayloads(map)
                .setExpiresAt(DateUtil.offsetHour(new Date(), EXPIRES))
                .setKey(KEY).sign();
    }

    public static Map<String, Object> getPayload(String token) {
        JWT jwt = new JWT(token);
        return jwt.getPayload().getClaimsJson();
    }

    public static boolean valid(String token) {
        return JWTUtil.verify(token, KEY);
    }
}
