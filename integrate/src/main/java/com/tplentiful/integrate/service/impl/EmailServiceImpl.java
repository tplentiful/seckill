package com.tplentiful.integrate.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.integrate.dao.EmailDao;
import com.tplentiful.integrate.pojo.dto.EmailSuffixDto;
import com.tplentiful.integrate.pojo.dto.SendMsgDto;
import com.tplentiful.integrate.pojo.po.EmailSuffix;
import com.tplentiful.integrate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-29
 */
@Service("emailService")
public class EmailServiceImpl extends ServiceImpl<EmailDao, EmailSuffix> implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${spring.mail.username}")
    private String fromUsername;


    @Override
    public EmailSuffixDto queryList() {
        List<String> emails = new ArrayList<>();
        for (EmailSuffix email : list(new QueryWrapper<EmailSuffix>().select("email"))) {
            emails.add(email.getEmail());
        }
        return new EmailSuffixDto(emails);
    }

    @Override
    public void sendMsg(SendMsgDto sendMsgDto) {
        String email = sendMsgDto.getEmail();
        String checkCode = RandomUtil.randomString(6);
        HashOperations<String, String, Object> op = stringRedisTemplate.opsForHash();
        Long codeCount = op.increment(email, RedisConstant.EMAIL_CODE_COUNT, 1);
        if (codeCount >= 2) {
            throw new BizException("请不要频繁获取验证码");
        }
        op.put(email, RedisConstant.EMAIL_CODE, checkCode);
        sendMsgDto.setCheckCode(checkCode);
        sendData(sendMsgDto);
        stringRedisTemplate.expire(email, 1, TimeUnit.MINUTES);
    }


    private void sendData(SendMsgDto sendMsgDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromUsername);
            helper.setTo(sendMsgDto.getEmail());
            helper.setSubject("Tp 秒杀商城登录验证码");
            helper.setText("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title>欢迎登录</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      background: #fff;\n" +
                    "      border: 1px solid #ccc;\n" +
                    "      margin: 2%;\n" +
                    "      padding: 0 30px;\n" +
                    "    }\n" +
                    "\n" +
                    "    p {\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "      line-height: 30px;\n" +
                    "      font-size: 14px;\n" +
                    "      color: #333;\n" +
                    "      font-family: '宋体', arial, sans-serif;\n" +
                    "    }\n" +
                    "\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div style=\"line-height:40px;height:40px\">&nbsp;</div>\n" +
                    "<p class=\"content\" style=\"font-weight:bold\">亲爱的用户：</p>\n" +
                    "<div style=\"line-height:20px;height:20px\">&nbsp;</div>\n" +
                    "<p class=\"content\">您好！感谢您使用 Tp 商城，您的账号( <span class=\"content\" style=\"font-weight:bold\">" + sendMsgDto.getEmail() + "</span> )正在进行邮箱验证，本次请求的验证码为：\n" +
                    "</p>\n" +
                    "<p class=\"content\"><b style=\"font-size:18px;color:#7d5956\">" + sendMsgDto.getCheckCode() + "</b>\n" +
                    "  <span class=\"content\">(为了保障您帐号的安全性，请在 5 分钟内完成验证)</span>\n" +
                    "</p>\n" +
                    "<div style=\"line-height:80px;height:80px\">&nbsp;</div>\n" +
                    "<p class=\"content\">tplentiful</p>\n" +
                    "<p class=\"content\">" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "</p>\n" +
                    "</body>\n" +
                    "</html>\n", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮箱创建失败", e);
            throw new BizException("邮箱发送失败");
        }
    }


}
