package com.tplentiful.sys.pojo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class ForgetPasswordModel {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 8, max = 24, message = "密码长度为 8-24 之间")
    private String password;
}
