package com.tplentiful.integrate.controller;


import com.tplentiful.common.utils.TR;
import com.tplentiful.integrate.pojo.dto.EmailSuffixDto;
import com.tplentiful.integrate.pojo.dto.SendMsgDto;
import com.tplentiful.integrate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/suffix")
    public TR<EmailSuffixDto> list() {
      return TR.ok("", emailService.queryList());
    }


    @PostMapping("/send")
    public TR<Void> sendMsg(@Valid @RequestBody SendMsgDto sendMsgDto) {
        emailService.sendMsg(sendMsgDto);
        return TR.ok("验证码已发送");
    }

}

