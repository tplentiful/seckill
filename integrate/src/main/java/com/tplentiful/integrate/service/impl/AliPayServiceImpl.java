package com.tplentiful.integrate.service.impl;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoAuthRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoAuthResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.integrate.config.AlipayConfiguration;
import com.tplentiful.integrate.pojo.AlipayUserInfo;
import com.tplentiful.integrate.pojo.model.AlipayAuthCodeModel;
import com.tplentiful.integrate.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service("alipayService")
public class AliPayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfiguration.AlipayProperties alipayProperties;

    @Override
    public void toLogin() {
        try {
            AlipayUserInfoAuthRequest request = new AlipayUserInfoAuthRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("scopes", "auth_user");
            bizContent.put("return_url", alipayProperties.getReturnUrl());
            bizContent.put("state", Base64.encode("tplentiful"));
            request.setBizContent(bizContent.toJSONString());
            AlipayUserInfoAuthResponse response = alipayClient.pageExecute(request);
            String submitFormData = response.getBody();
            ServletRequestAttributes requestAttributes;
            HttpServletResponse httpServletResponse;
            if ((requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) == null
                    || (httpServletResponse = requestAttributes.getResponse()) == null) {
                throw new BizException("服务器异常");
            }
            httpServletResponse.setContentType("text/html; charset=UTF-8");
            httpServletResponse.getWriter().print(submitFormData);
        } catch (AlipayApiException e) {
            log.error("支付宝服务异常", e);
            throw new BizException("支付宝服务异常");
        } catch (IOException e) {
            log.error("重定向失败");
            throw new BizException("服务异常");
        }
    }

    @Override
    public void getAuthCode(AlipayAuthCodeModel authCodeModel) {
        AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
        AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
        oauthTokenRequest.setCode(authCodeModel.getAuth_code());
        oauthTokenRequest.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
            log.info("token: {}, userId: {}", oauthTokenResponse.getAccessToken(), oauthTokenResponse.getUserId());
            AlipayUserInfoShareResponse userInfoShareResponse = alipayClient.execute(userInfoShareRequest, oauthTokenResponse.getAccessToken());
            if (userInfoShareResponse.isSuccess()) {
                log.info("{}",  JSON.parseObject(userInfoShareResponse.getBody()).get("alipay_user_info_share_response"));
                AlipayUserInfo userInfo = JSON.parseObject(JSON.toJSONString(JSON.parseObject(userInfoShareResponse.getBody()).get("alipay_user_info_share_response")), AlipayUserInfo.class);
                log.info("当前用户信息: {}", userInfo);
            }
        } catch (AlipayApiException e) {
            log.error("支付宝登录逻辑异常", e);
            throw new BizException("支付宝登录失败");
        }
    }

}
