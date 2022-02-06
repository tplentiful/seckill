package com.tplentiful.sys.config;

import com.tplentiful.sys.security.TpCheckCodeAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TpCheckCodeAuthenticationProvider;
import com.tplentiful.sys.security.TpPasswordAuthenticationProvider;
import com.tplentiful.sys.security.TpPasswordAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TpUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TpUserDetailService tpUserDetailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TpPasswordAuthenticationProvider passwordProvider
                = new TpPasswordAuthenticationProvider();
        passwordProvider.setPasswordEncoder(new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B));
        passwordProvider.setUserDetailsService(tpUserDetailService);
        TpCheckCodeAuthenticationProvider checkCodeProvider
                = new TpCheckCodeAuthenticationProvider(stringRedisTemplate);
        checkCodeProvider.setPasswordEncoder(new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B));
        checkCodeProvider.setUserDetailsService(tpUserDetailService);
        http.authorizeRequests(req -> req
                        .antMatchers("/menu/**").hasRole("admin")
                        .antMatchers("/user/passwordLogin", "/user/checkCodeLogin").permitAll()
                        .antMatchers("/table/**", "/user/**").permitAll()
                        .anyRequest()
                        .authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .formLogin().disable()
                .logout(LogoutConfigurer::permitAll)
                .addFilterAt(new TpPasswordAuthenticationProcessingFilter(passwordProvider,
                        "/user/passwordLogin"), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new TpCheckCodeAuthenticationProcessingFilter(checkCodeProvider,
                        "/user/checkCodeLogin"), UsernamePasswordAuthenticationFilter.class);
    }

}
