package com.tplentiful.sys.config;

import com.tplentiful.sys.security.TPJWTAuthentication;
import com.tplentiful.sys.security.TPCheckCodeAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TPCheckCodeAuthenticationProvider;
import com.tplentiful.sys.security.TPJwtUtil;
import com.tplentiful.sys.security.TPLogoutHandler;
import com.tplentiful.sys.security.TPLogoutSuccessHandler;
import com.tplentiful.sys.security.TPPasswordAuthenticationProcessingFilter;
import com.tplentiful.sys.security.TPPasswordAuthenticationProvider;
import com.tplentiful.sys.security.TPUserDetailService;
import com.tplentiful.sys.security.TPWebSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
    private TPUserDetailService tpUserDetailService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TPWebSecurityExpressionHandler tpWebSecurityExpressionHandler;


    @Bean
    SecurityFilterChain web(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> access)
            throws Exception {
        TPPasswordAuthenticationProvider passwordProvider
                = new TPPasswordAuthenticationProvider();
        passwordProvider.setPasswordEncoder(TPJwtUtil.bCryptPasswordEncoder);
        passwordProvider.setUserDetailsService(tpUserDetailService);
        TPCheckCodeAuthenticationProvider checkCodeProvider
                = new TPCheckCodeAuthenticationProvider(stringRedisTemplate);
        checkCodeProvider.setPasswordEncoder(TPJwtUtil.bCryptPasswordEncoder);
        checkCodeProvider.setUserDetailsService(tpUserDetailService);
        http.authorizeHttpRequests((authorize) -> authorize
                        .mvcMatchers("/loginStatistics/**","/user/checkCodeLogin", "/user/passwordLogin", "/user/**").permitAll()
                        .antMatchers("/menu/**").hasRole("ADMIN")
                        .anyRequest().access(access)
                ).csrf(AbstractHttpConfigurer::disable)
                .logout(config -> config.logoutUrl("/user/logout").addLogoutHandler(new TPLogoutHandler(stringRedisTemplate)).logoutSuccessHandler(new TPLogoutSuccessHandler()))
                .addFilterAt(new TPPasswordAuthenticationProcessingFilter(passwordProvider,
                        "/user/passwordLogin", stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new TPCheckCodeAuthenticationProcessingFilter(checkCodeProvider,
                        "/user/checkCodeLogin", stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TPJWTAuthentication(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthorizationManager(HandlerMappingIntrospector introspector) {
        RequestMatcher any = AnyRequestMatcher.INSTANCE;
        AuthorizationManager<HttpServletRequest> manager = RequestMatcherDelegatingAuthorizationManager.builder()
                .add(any, AuthenticatedAuthorizationManager.authenticated())
                .build();
        return (context, o) -> manager.check(context, o.getRequest());
    }


}
