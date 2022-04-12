package com.tplentiful.sys.controller;


import com.tplentiful.common.utils.TR;
import com.tplentiful.sys.pojo.dto.MenuDto;
import com.tplentiful.sys.pojo.dto.MenuListDto;
import com.tplentiful.sys.pojo.model.MenuSaveAndModModel;
import com.tplentiful.sys.pojo.po.Menu;
import com.tplentiful.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public TR<List<MenuListDto>> list(Long id) {
        return TR.ok("", menuService.getMenuList(id));
    }

    @PostMapping("/addMenu")
    public TR<Void> list(@RequestBody MenuSaveAndModModel menuSaveModel) {
        menuService.addMenu(menuSaveModel);
        return TR.ok("");
    }

    @PostMapping("/update")
    public TR<Void> update(@RequestBody MenuSaveAndModModel menuModModel) {
        menuService.updateOneById(menuModModel);
        return TR.ok("菜单修改成功");
    }

    @PostMapping("/del")
    public TR<Void> del(@RequestBody Long[] ids) {
        menuService.delMenu(ids);
        return TR.ok("删除成功");
    }

    @GetMapping("/getOne")
    public TR<MenuDto> getOne(@RequestParam("id") Long id) {
        return TR.ok("", menuService.getOneById(id));
    }
}

