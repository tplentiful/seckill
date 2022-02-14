package com.tplentiful.integrate.controller;

import com.tplentiful.integrate.pojo.model.QQAuthCodeModel;
import com.tplentiful.integrate.service.QQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@RestController
@RequestMapping("/qq")
public class QQController {
@Autowired
private QQService qqService;


    @GetMapping("/toLogin")
    public void toLogin() {
        qqService.toLogin();
    }

    @GetMapping("/notify")
    public void authCode(QQAuthCodeModel model) {
        qqService.getAccessToken(model);
    }
}
