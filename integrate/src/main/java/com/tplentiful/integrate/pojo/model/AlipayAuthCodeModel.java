package com.tplentiful.integrate.pojo.model;

import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class AlipayAuthCodeModel {
    private String app_id;
    private String source;
    private String scope;
    private String auth_code;
}
