package com.tplentiful.integrate.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class SendMsgDto {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String checkCode;
}
