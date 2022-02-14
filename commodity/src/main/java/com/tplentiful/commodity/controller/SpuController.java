package com.tplentiful.commodity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tplentiful.commodity.pojo.po.Spu;
import com.tplentiful.commodity.service.SpuService;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * spu 表 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/list")
    public TR<IPage<Spu>> list(PageModel pageModel) {
        return TR.ok("ok", spuService.queryPage(pageModel));
    }

    @GetMapping("/getOne/{id}")
    public TR<Spu> getOne(@PathVariable Long id) {
        return TR.ok("", spuService.getById(id));
    }


    @PostMapping("/save")
    public TR<Void> saveOne(@RequestBody Spu spu) {
        spuService.save(spu);
        return TR.ok("添加成功");
    }

    @PostMapping("/update")
    public TR<Void> delete(@RequestBody Spu spu) {
        spuService.updateById(spu);
        return TR.ok("更新成功");
    }


    @PostMapping("/del")
    public TR<Void> delete(@RequestBody Long[] ids) {
        spuService.removeBatchByIds(Arrays.asList(ids));
        return TR.ok("删除成功");
    }
}

