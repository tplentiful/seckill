package com.tplentiful.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.CategoryDao;
import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {


    @Override
    public Map<Long, CategoryDto> getCategoryList() {
        Map<Long, CategoryDto> res = new HashMap<>();
        Map<Long, List<CategoryDto>> secondMap = new HashMap<>();
        Map<Long, CategoryDto> tempSecondMap = new HashMap<>();
        List<Category> allData = list();
        Long parentId;
        for (Category allDatum : allData) {
            parentId = allDatum.getParentId();
            if (parentId.equals(0L)) {
                res.put(allDatum.getId(), CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build());
            } else if (allDatum.getGrade().equals(2)) {
                List<CategoryDto> tempList = secondMap.getOrDefault(parentId, new ArrayList<>());
                CategoryDto categoryDto = CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build();
                tempList.add(categoryDto);
                secondMap.put(parentId, tempList);
                tempSecondMap.put(categoryDto.getId(), categoryDto);
            }
        }
        for (Category allDatum : allData) {
            if (allDatum.getGrade().equals(3)) {
                parentId = allDatum.getParentId();
                List<CategoryDto> children = tempSecondMap.get(parentId).getChildren();
                children.add(CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build());
            }
        }
        for (Long key : res.keySet()) {
            CategoryDto categoryDto = res.get(key);
            categoryDto.setChildren( secondMap.get(key));
        }
        return res;
    }


    // 一级目录
    // allData.stream().map(category -> {
    //     if (!category.getParentId().equals(0L)) {
    //     }
    // })
}
