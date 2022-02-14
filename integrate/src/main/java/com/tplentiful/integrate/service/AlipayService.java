package com.tplentiful.integrate.service;

import com.tplentiful.integrate.pojo.model.AlipayAuthCodeModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public interface AlipayService {
    void toLogin();

    void getAuthCode(AlipayAuthCodeModel authCodeModel);
}
