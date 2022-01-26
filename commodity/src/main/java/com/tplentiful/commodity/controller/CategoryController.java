package com.tplentiful.commodity.controller;


import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.service.CategoryService;
import com.tplentiful.common.utils.TR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public TR<Map<Long, CategoryDto>> list() {

        return TR.ok("成功",categoryService.getCategoryList());
    }
}

