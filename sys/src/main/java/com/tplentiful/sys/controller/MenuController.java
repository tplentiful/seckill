package com.tplentiful.sys.controller;


import com.tplentiful.common.utils.TR;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @GetMapping("/test")
    public TR<Void> test() {
        return TR.ok("ok");
    }
}

