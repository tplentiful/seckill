package com.tplentiful.sys.security;

import com.tplentiful.common.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TpCheckCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private volatile String userNotFoundEncodedPassword;
    private StringRedisTemplate stringRedisTemplate;

    public TpCheckCodeAuthenticationProvider() {
        // 当你需要自定义一下用户名未找到的异常信息的时候需要将这个设置为false
        super.setHideUserNotFoundExceptions(false);
    }

    public TpCheckCodeAuthenticationProvider(StringRedisTemplate stringRedisTemplate) {
        // 当你需要自定义一下用户名未找到的异常信息的时候需要将这个设置为false
        super.setHideUserNotFoundExceptions(false);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String getUserNotFoundEncodedPassword() {
        return userNotFoundEncodedPassword;
    }

    public void setUserNotFoundEncodedPassword(String userNotFoundEncodedPassword) {
        this.userNotFoundEncodedPassword = userNotFoundEncodedPassword;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.error("请求凭证为空");
            throw new BadCredentialsException("未授权用户");
        }
        // 需要比对的密码
        String presentedPassword = authentication.getCredentials().toString();
        if (!(userDetails instanceof TpUser)) {
            log.error("请求信息转换错误");
            throw new BadCredentialsException("登录信息格式有误");
        }
        TpUser user = (TpUser) userDetails;
        String email = user.getUsername();
        // 拿到的密码
        Long expire = stringRedisTemplate.getExpire(email);
        if (expire == null || expire <= 0) {
            throw new BadCredentialsException("验证码有误");
        }
        HashOperations<String, String, Object> operations = stringRedisTemplate.opsForHash();
        Object checkCodeObj = operations.get(email, RedisConstant.EMAIL_CODE);
        if (checkCodeObj == null || !checkCodeObj.equals(presentedPassword)) {
            throw new BadCredentialsException("验证码有误");
        }
        operations.delete(email, RedisConstant.EMAIL_CODE);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return this.getUserDetailsService().loadUserByUsername(username);
    }


}
