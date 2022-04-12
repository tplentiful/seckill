package com.tplentiful.sys.service;

import com.tplentiful.sys.pojo.dto.MenuDto;
import com.tplentiful.sys.pojo.dto.MenuListDto;
import com.tplentiful.sys.pojo.model.MenuSaveAndModModel;
import com.tplentiful.sys.pojo.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
public interface MenuService extends IService<Menu> {

    List<MenuListDto> getMenuList(Long id);

    void addMenu(MenuSaveAndModModel menu);

    MenuDto getOneById(Long id);

    void updateOneById(MenuSaveAndModModel menuModModel);

    void delMenu(Long[] ids);
}
