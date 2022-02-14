package com.tplentiful.sys.config;

import com.tplentiful.sys.security.TpCheckCodeAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TpCheckCodeAuthenticationProvider;
import com.tplentiful.sys.security.TpJwtUtil;
import com.tplentiful.sys.security.TpPasswordAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TpPasswordAuthenticationProvider;
import com.tplentiful.sys.security.TpUserDetailService;
import com.tplentiful.sys.security.TpWebSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private TpUserDetailService tpUserDetailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TpWebSecurityExpressionHandler tpWebSecurityExpressionHandler;


    @Bean
    SecurityFilterChain web(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> access)
            throws Exception {
        TpPasswordAuthenticationProvider passwordProvider
                = new TpPasswordAuthenticationProvider();
        passwordProvider.setPasswordEncoder(TpJwtUtil.bCryptPasswordEncoder);
        passwordProvider.setUserDetailsService(tpUserDetailService);
        TpCheckCodeAuthenticationProvider checkCodeProvider
                = new TpCheckCodeAuthenticationProvider(stringRedisTemplate);
        checkCodeProvider.setPasswordEncoder(TpJwtUtil.bCryptPasswordEncoder);
        checkCodeProvider.setUserDetailsService(tpUserDetailService);
        http.authorizeHttpRequests((authorize) -> authorize
                        .mvcMatchers("/user/checkCodeLogin", "/user/passwordLogin", "/user/**").permitAll()
                        .anyRequest().access(access)
                ).csrf(AbstractHttpConfigurer::disable)
                // TODO 登出的逻辑后续再说吧
                .addFilterAt(new TpPasswordAuthenticationProcessingFilter(passwordProvider,
                        "/user/passwordLogin"), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new TpCheckCodeAuthenticationProcessingFilter(checkCodeProvider,
                        "/user/checkCodeLogin"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthorizationManager(HandlerMappingIntrospector introspector) {
        RequestMatcher admin = new MvcRequestMatcher(introspector, "/menu/**");
        RequestMatcher any = AnyRequestMatcher.INSTANCE;
        AuthorizationManager<HttpServletRequest> manager = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(admin, AuthorityAuthorizationManager.hasRole("admin"))
                .add(any, AuthenticatedAuthorizationManager.authenticated())
                .build();
        return (context, o) -> manager.check(context, o.getRequest());
    }



}
