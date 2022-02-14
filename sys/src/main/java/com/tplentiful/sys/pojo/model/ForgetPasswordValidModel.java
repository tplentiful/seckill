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
public class ForgetPasswordValidModel {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码长度为 6")
    private String checkCode;
}
