package com.tplentiful.integrate.controller;

import com.tplentiful.integrate.pojo.model.AlipayAuthCodeModel;
import com.tplentiful.integrate.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/toLogin")
    public void toLogin() {
        alipayService.toLogin();
    }

    @GetMapping("/notify")
    public void notify(AlipayAuthCodeModel alipayAuthCodeModel) {
        alipayService.getAuthCode(alipayAuthCodeModel);
    }

}
