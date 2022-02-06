package com.tplentiful.commodity.service;

import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface CategoryService extends IService<Category> {

    Map<Long, CategoryDto> getCategoryList();

    Category getInfoById(Long id);

    void updateAndSyncCache(Category category);

    void assembleAndSave(List<CategoryDto> categoryDtos);
}
