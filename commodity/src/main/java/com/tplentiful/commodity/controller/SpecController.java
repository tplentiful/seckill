package com.tplentiful.commodity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.model.SpecSaveModel;
import com.tplentiful.commodity.pojo.po.Spec;
import com.tplentiful.commodity.service.SpecService;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TR;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 规格参数表 spu 属性 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @ApiOperation("商品参数列表查询")
    @GetMapping("/list/{cid}")
    public TR<IPage<Spec>> list(@ApiParam("0 | null 查询全部，其余根据 Category 查询") @PathVariable("cid") Long cid,
                                @ApiParam("查询的分页参数") PageModel pageModel) {
        IPage<Spec> page = specService.queryPage(cid, pageModel);
        return TR.ok("", page);
    }

    @ApiOperation("查询全量的数据")
    @GetMapping("/all")
    public TR<List<Spec>> all(
            @ApiParam("关键字") @RequestParam(value = "key", required = false) String key,
            @ApiParam("0 | null 查询全部，其余根据 Category 查询") @RequestParam(value = "cid", required = false) Long cid) {
        return TR.ok("", specService.getAllData(key, cid));
    }


    @PostMapping("/save")
    public TR<Void> saveOne(@Valid @RequestBody SpecSaveModel specSaveModel) {
        specService.saveOne(specSaveModel);
        return TR.ok("新增商品");
    }

    @PostMapping("/update")
    public TR<Void> updateOne(@RequestBody Spec spec) {
        specService.updateById(spec);
        return TR.ok("修改成功");
    }

    @PostMapping("/del")
    public TR<Void> del(@RequestBody Long[] ids) {
        specService.removeByIds(Arrays.asList(ids));
        return TR.ok("删除成功");
    }

    @GetMapping("/get/{id}")
    public TR<Spec> getOne(@PathVariable("id") Long id) {
        return TR.ok("", specService.getOneById(id));
    }
}

