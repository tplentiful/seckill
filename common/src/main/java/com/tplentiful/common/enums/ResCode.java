package com.tplentiful.common.enums;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public enum ResCode {
    FAIL(-1), // 失败 一般是服务器错误
    OK(0), // 成功
    NO_PASSWD(41), // 密码错误
    NO_USER(42), // 没有这个用户
    NO_PERM(45); // 没有权限

    private int code;

    private ResCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
