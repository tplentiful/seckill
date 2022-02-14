package com.tplentiful.integrate.pojo;

import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class QQAccessToken {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
}
