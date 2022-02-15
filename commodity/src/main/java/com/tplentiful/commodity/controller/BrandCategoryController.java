package com.tplentiful.commodity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.po.BrandCategory;
import com.tplentiful.commodity.service.BrandCategoryService;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/brandCategory")
public class BrandCategoryController {
    @Autowired
    private BrandCategoryService brandCategoryService;


    @PostMapping("/addRelation")
    public TR<Void> addRelation(@RequestBody BrandCategory brandCategory) {
        brandCategoryService.addRelation(brandCategory);
        return TR.ok("新增一条关联关系");
    }


    @PostMapping("/delRelation")
    public TR<Void> delRelation(@RequestBody BrandCategory brandCategory) {
        brandCategoryService.delRelation(brandCategory);
        return TR.ok("关系解除成功");
    }
}

