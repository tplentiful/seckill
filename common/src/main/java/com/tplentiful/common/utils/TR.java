package com.tplentiful.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tplentiful.common.enums.ResCode;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class TR<T> {
    private T data;
    private String msg;
    private Integer code;

    public TR(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public TR(Integer code, String msg) {
        this(code, msg, null);
    }

    public static <T> TR<T> ok(String msg, T data) {
        return new TR<>(ResCode.OK.getCode(), msg, data);
    }

    public static TR<Void> ok(String msg) {
        return new TR<>(ResCode.OK.getCode(), msg, null);
    }

    public static <T> TR<T> fail(String msg, T data) {
        return new TR<>(ResCode.FAIL.getCode(), msg, data);
    }

    public static TR<Void> fail(String msg) {
        return new TR<>(ResCode.FAIL.getCode(), msg, null);
    }

    /**
     * 获取 序列化的数据
     *
     * @param clazz
     * @return
     */
    public T getData(Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    /**
     * 获取序列化的数据
     *
     * @param typeReference
     * @return
     */
    public T getData(TypeReference<T> typeReference) {
        return JSON.parseObject(JSON.toJSONString(data), typeReference);
    }

}
