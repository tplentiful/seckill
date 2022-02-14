package com.tplentiful.integrate.pojo;

import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class AlipayUserInfo {
    private String code;
    private String msg;
    private String avatar;
    private String gender;
    private String is_certified;
    private String is_student_certified;
    private String nick_name;
    private String user_id;
    private String user_status;
    private String user_type;
}
