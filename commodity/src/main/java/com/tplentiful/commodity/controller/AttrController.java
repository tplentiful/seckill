package com.tplentiful.commodity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.dto.AttrDto;
import com.tplentiful.commodity.pojo.model.AttrSaveAndModModel;
import com.tplentiful.commodity.pojo.po.Attr;
import com.tplentiful.commodity.service.AttrService;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TR;
import com.tplentiful.common.validtor.ModValidator;
import com.tplentiful.common.validtor.SaveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @PostMapping("/save")
    public TR<Void> saveOne(@Validated({SaveValidator.class}) @RequestBody AttrSaveAndModModel model) {
        attrService.saveOne(model);
        return TR.ok("属性新增成功");
    }

    @GetMapping("/all")
    public TR<List<Attr>> all(@RequestParam(value = "key", required = false) String key) {
       return TR.ok("", attrService.getAllData(key));
    }

    @GetMapping("/list")
    public TR<IPage<Attr>> list(PageModel pageModel) {
        return TR.ok("", attrService.queryList(pageModel));
    }

    @GetMapping("/one/{id}")
    public TR<AttrDto> getOne(@PathVariable("id") Long id) {
        return TR.ok("", attrService.queryOne(id));
    }

    @PostMapping("/mod")
    public TR<Void> modOne(@Validated({ModValidator.class})@RequestBody AttrSaveAndModModel model) {
        attrService.modOne(model);
        return TR.ok("修改成功");
    }

    @PostMapping("/del")
    public TR<Void> delBatchByIds(@RequestBody Long[] id) {
        attrService.deleteBatchByIds(id);
        return TR.ok("删除成功");
    }
}

