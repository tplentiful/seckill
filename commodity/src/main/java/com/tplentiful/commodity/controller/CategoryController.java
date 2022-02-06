package com.tplentiful.commodity.controller;


import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.model.CategorySaveListModel;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.CategoryService;
import com.tplentiful.common.utils.TR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
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

    @PostMapping("/saves")
    public TR<Void> saveList(@RequestBody CategorySaveListModel model) {
        categoryService.assembleAndSave(model.getCategoryDtos());
        return TR.ok("当前状态保存成功");
    }

    @GetMapping("/list")
    public TR<Map<Long, CategoryDto>> list() {
        return TR.ok("类目栏获取成功", categoryService.getCategoryList());
    }

    @GetMapping("/one")
    public TR<Category> getOne(Long id) {
        return TR.ok("类目栏节点信息获取成功", categoryService.getInfoById(id));
    }

    @PostMapping("/mod")
    public TR<Void> modOne(@RequestBody Category category) {
        categoryService.updateAndSyncCache(category);
        return TR.ok("修改成功");
    }

    @PostMapping("/delete")
    public TR<Void> delete(Long[] ids) {
        categoryService.removeBatchByIds(Arrays.asList(ids));
        return TR.ok("删除成功" + Arrays.toString(ids));
    }
}

