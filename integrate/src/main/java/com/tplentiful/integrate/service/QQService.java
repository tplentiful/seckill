package com.tplentiful.integrate.service;

import com.tplentiful.integrate.pojo.model.QQAuthCodeModel;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public interface QQService {


    void toLogin();

    void getAccessToken(QQAuthCodeModel model);
}
