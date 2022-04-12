package com.tplentiful.sys.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.StringConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.sys.dao.MenuDao;
import com.tplentiful.sys.pojo.dto.MenuDto;
import com.tplentiful.sys.pojo.dto.MenuListDto;
import com.tplentiful.sys.pojo.model.MenuSaveAndModModel;
import com.tplentiful.sys.pojo.po.Menu;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.service.MenuService;
import com.tplentiful.sys.service.PermService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private PermService permService;

    @Override
    public List<MenuListDto> getMenuList(Long id) {
        List<Perm> permsByEmail = permService.getPermsById(id);
        if (CollectionUtils.isEmpty(permsByEmail)) {
            throw new BizException("无法获取菜单");
        }
        List<Menu> menuList = list(new QueryWrapper<Menu>().orderByAsc("grade", "sort"));
        Iterator<Menu> iterator = menuList.iterator();
        boolean isDel = true;
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            for (Perm perm : permsByEmail) {
                if (menu.getPerms().contains(perm.getName())) {
                    isDel = false;
                    break;
                }
            }
            if (isDel) {
                iterator.remove();
            }
            isDel = true;
        }
        List<MenuListDto> res = new LinkedList<>();
        Map<Long, List<MenuListDto>> secondMenuMap = new LinkedHashMap<>();
        List<MenuListDto> secondMenuList = new LinkedList<>();
        Map<Long, List<MenuListDto>> thirdMenuMap = new LinkedHashMap<>();
        // 做封装
        for (Menu menu : menuList) {
            Long menuTempParentId = menu.getParentId();
            MenuListDto menuListDto = new MenuListDto();
            BeanUtils.copyProperties(menu, menuListDto);
            menuListDto.setMeta(JSON.parseObject(menu.getMeta()));
            if (menuTempParentId == 0) {
                res.add(menuListDto);
            } else if (menu.getGrade() == 2) {
                List<MenuListDto> tempChildren = secondMenuMap.getOrDefault(menuTempParentId, new LinkedList<>());
                if (CollectionUtils.isEmpty(tempChildren)) {
                    secondMenuMap.put(menuTempParentId, tempChildren);
                }
                tempChildren.add(menuListDto);
                secondMenuList.add(menuListDto);
            } else {
                List<MenuListDto> tempChildren = thirdMenuMap.getOrDefault(menuTempParentId, new LinkedList<>());
                if (CollectionUtils.isEmpty(tempChildren)) {
                    thirdMenuMap.put(menuTempParentId, tempChildren);
                }
                tempChildren.add(menuListDto);
            }
        }
        for (MenuListDto menuListDto : secondMenuList) {
            menuListDto.setChildren(thirdMenuMap.get(menuListDto.getId()));
        }
        for (MenuListDto menuListDto : res) {
            menuListDto.setChildren(secondMenuMap.get(menuListDto.getId()));
        }
        return res;
    }

    @Override
    public void addMenu(MenuSaveAndModModel menuSaveModel) {
        List<Perm> perms = permService.getPermsById(menuSaveModel.getUserId());
        List<String> permStrList = perms.stream().map(Perm::getName).collect(Collectors.toList());
        if (!permStrList.contains("ADMIN")) {
            throw new BizException("您的权限不够");
        }
        Menu menu = getMenu(menuSaveModel);
        menu.setId(null);
        save(menu);
    }

    @Override
    public MenuDto getOneById(Long id) {
        Menu menu = getById(id);
        if (ObjectUtils.isEmpty(menu)) {
            throw new BizException("当前菜单不存在");
        }
        MenuDto menuDto = new MenuDto();
        String path = menu.getPath().substring(1).replace("/", ":");
        menu.setPath(path);
        menuDto.setMeta(JSON.parseObject(menu.getMeta()));
        BeanUtils.copyProperties(menu, menuDto);
        return menuDto;
    }

    // @Override
    // public MenuDto getOneById(Long id) {
    //     Map<Long, Menu> map = list().stream().collect(Collectors.toMap(Menu::getId, (o) -> o));
    //     Menu menu = map.get(id);
    //     if (ObjectUtils.isEmpty(menu)) {
    //         throw new BizException("当前菜单不存在");
    //     }
    //     return getParentMenu(map, menu);
    // }

    private MenuDto getParentMenu(Map<Long, Menu> map, Menu menu) {
        Long parentId = menu.getParentId();
        MenuDto menuDto = new MenuDto();
        String path = menu.getPath().substring(1).replace("/", ":");
        menu.setPath(path);
        BeanUtils.copyProperties(menu, menuDto);
        if (parentId == 0) {
            return menuDto;
        }
        Menu fatherMenu = map.get(parentId);
        MenuDto parentMenu = getParentMenu(map, fatherMenu);
        parentMenu.setChild(menuDto);
        return parentMenu;
    }

    @Override
    public void updateOneById(MenuSaveAndModModel menuModModel) {
        List<Perm> perms = permService.getPermsById(menuModModel.getUserId());
        List<String> permStrList = perms.stream().map(Perm::getName).collect(Collectors.toList());
        if (!permStrList.contains("ADMIN")) {
            throw new BizException("您的权限不够");
        }
        List<String> permsStrList = permService.list().stream().map(Perm::getName).collect(Collectors.toList());
        String[] split = menuModModel.getPerms();
        for (String permStr : split) {
            if (!permsStrList.contains(permStr)) {
                throw new BizException("当前菜单权限格式有误");
            }
        }
        Menu menu = getMenu(menuModModel);
        // 修改信息的 Path 可以不用改
        updateById(menu);
    }

    private Menu getMenu(MenuSaveAndModModel menuModel) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuModel, menu);
        Long parentId = menuModel.getParentId();
        if (parentId == null || parentId < 1) {
            menu.setParentId(0L);
        } else {
            menu.setParentId(parentId);
        }
        String meta = JSON.toJSONString(menuModel.getMeta());
        menu.setMeta(meta);
        String permsStr = ArrayUtil.join(menuModel.getPerms(), StringConstant.STRING_SPLIT);
        menu.setPerms(permsStr);
        // 修改一下 path
        String path = menu.getPath();
        path = path.replace("/", ":").replace(":", "/");
        menu.setPath("/" + path);
        return menu;
    }

    @Override
    public void delMenu(Long[] ids) {
        if (ids.length == 0) {
            throw new BizException("删除失败");
        }
        List<Long> idList = Arrays.asList(ids);
        List<Menu> menuList = list(new QueryWrapper<Menu>().in("parent_id", idList).select("id"));
        if (!CollectionUtils.isEmpty(menuList)) {
            throw new BizException("菜单存在子目录无法删除");
        }
        removeBatchByIds(idList);
    }
}
