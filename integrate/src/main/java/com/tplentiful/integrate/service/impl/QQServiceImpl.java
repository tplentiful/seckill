package com.tplentiful.integrate.service.impl;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.integrate.config.QQConfiguration;
import com.tplentiful.integrate.pojo.QQAccessToken;
import com.tplentiful.integrate.pojo.model.QQAuthCodeModel;
import com.tplentiful.integrate.service.QQService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service("qqService")
public class QQServiceImpl implements QQService {


    private final Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA);

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private QQConfiguration.QQProperties properties;

    public void qqToAnyWhere(String url) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response;
        if (attributes == null) {
            log.error("上下文异常 attributes == null");
            throw new BizException("qq服务异常");
        }
        response = attributes.getResponse();
        if (response == null) {
            log.error("上下文异常 response == null");
            throw new BizException("qq服务异常");
        }
        try {
            //  auth-url: https://graph.qq.com/oauth2.0/authorize?
            //  response_type=%s
            //  &client_id=%s
            //  &state=%s
            //  &display=%s
            //  &redirect_uri=%s
            log.info("当前 url: {}", url);
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error("抛出异常 qq 服务", e);
            throw new BizException("qq 服务异常");
        }

    }

    @Override
    public void toLogin() {
        String encodeUrl = URLEncodeUtil.encodeAll(properties.getRedirectUri());
        log.info("编码后: {}", encodeUrl);
        qqToAnyWhere(String.format(properties.getAuthUrl(),
                properties.getResponseType(),
                properties.getClientId(),
                sign.signHex("tplentiful"),
                properties.getDisplay(), encodeUrl));
    }

    @Override
    public void getAccessToken(QQAuthCodeModel model) {
        if ((sign.verify(StrUtil.bytes("tplentiful"), StrUtil.bytes(model.getState())))) {
            log.error("当前返回状态不统一");
            throw new BizException("非法参数");
        }
        //  token-url: https://graph.qq.com/oauth2.0/token?
        //  grant_type=%s
        //  &client_id=%s
        //  &client_secret=%s
        //  &code=%s
        //  &fmt=%s
        //  &redirect_uri=%s
        String encodeUrl = URLEncodeUtil.encodeAll(properties.getRedirectUri());
        try {
            Response response = okHttpClient.newCall(new Request.Builder().url(String.format(properties.getTokenUrl(),
                    "authorization_code",
                    properties.getClientId(),
                    properties.getClientSecret(),
                    model.getCode(),
                    properties.getFmt(),
                    encodeUrl)).build()).execute();
            ResponseBody body = response.body();
            if (!response.isSuccessful()) {
                log.error("qq 服务响应失败");
                throw new BizException("qq 登录失败");
            }
            if (!ObjectUtils.isEmpty(body)) {
                String resBodyStr = body.string();
                JSONObject resBodyJson = JSON.parseObject(resBodyStr);
                Object errorDesc = resBodyJson.get("error_description");
                if (!ObjectUtils.isEmpty(errorDesc)) {
                    log.error("获取 access_token 失败: {}", errorDesc);
                    throw new BizException("qq 登录失败");
                }
                QQAccessToken qqAccessToken = JSON.parseObject(resBodyStr, QQAccessToken.class);
                Response openIDRes = okHttpClient.newCall(new Request.Builder().url(String.format(properties.getOpenIdURL(), qqAccessToken.getAccessToken(), properties.getFmt())).build()).execute();
                if (!openIDRes.isSuccessful()) {
                    log.error("qq 服务响应失败");
                    throw new BizException("qq 登录失败");
                }
                ResponseBody openIDResBody = openIDRes.body();
                if (!ObjectUtils.isEmpty(openIDResBody)) {
                    String openIDResStr = openIDResBody.string();
                    JSONObject openIDResJSon = JSON.parseObject(openIDResStr);
                    Object openIDErrDesc = openIDResJSon.get("error_description");
                    if (!ObjectUtils.isEmpty(openIDErrDesc)) {
                        log.error("获取 openID 失败: {}", openIDErrDesc);
                        throw new BizException("qq 登录失败");
                    }
                    log.info("{}", openIDResJSon);
                    qqToAnyWhere("http://tplentiful.bio/");
                }
            }
        } catch (IOException e) {
            log.error("qq 登录失败", e);
            throw new BizException("qq 登录失败");
        }
    }
}
