package com.tplentiful.common.config;

import com.alibaba.fastjson.JSONObject;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.TR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@RestControllerAdvice
public class AdviseController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public TR<JSONObject> ArgumentError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        JSONObject errorInfo = new JSONObject();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorInfo.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("方法参数异常: {}", e.getMessage());
        return TR.fail("参数错误", errorInfo);
    }

    @ExceptionHandler(BizException.class)
    public TR<Void> serverError(BizException e) {
        log.error("业务异常", e);
        return TR.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public TR<Void> serverError(Exception e) {
        log.error("服务器异常", e);
        return TR.fail("服务器异常");
    }

}
