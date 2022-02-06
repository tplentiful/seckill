package com.tplentiful.integrate.service;

import com.tplentiful.integrate.pojo.dto.EmailSuffixDto;
import com.tplentiful.integrate.pojo.dto.SendMsgDto;
import com.tplentiful.integrate.pojo.po.EmailSuffix;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-29
 */
public interface EmailService extends IService<EmailSuffix> {

    EmailSuffixDto queryList();

    void sendMsg(SendMsgDto sendMsgDto);
}
