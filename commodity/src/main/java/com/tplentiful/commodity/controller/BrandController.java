package com.tplentiful.commodity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.model.SaveBrandModel;
import com.tplentiful.commodity.pojo.po.Brand;
import com.tplentiful.commodity.service.BrandService;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list/{firstname}")
    public TR<IPage<Brand>> list(@PathVariable("firstname") Integer firstname, PageModel pageModel) {
        return TR.ok("", brandService.queryPage(firstname, pageModel));
    }

    @PostMapping("/save")
    public TR<Void> saveOne(@Valid SaveBrandModel brand) {
        brandService.saveBrand(brand);
        return TR.ok("新增品牌");
    }

    @PostMapping("/del")
    public TR<Void> del(@RequestBody Long[] ids) {
        brandService.deleteBatchById(ids);
        return TR.ok("删除成功");
    }

}

