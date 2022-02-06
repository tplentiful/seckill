package com.tplentiful.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.CategoryDao;
import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.CategoryService;
import com.tplentiful.common.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Map<Long, CategoryDto> getCategoryList() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        Map<Long, CategoryDto> res = JSON.parseObject(operations.get(RedisConstant.COMMODITY_CATEGORY), new TypeReference<Map<Long, CategoryDto>>() {
        });
        if (!CollectionUtils.isEmpty(res)) {
            return res;
        }
        res = new LinkedHashMap<>();
        Map<Long, CategoryDto> tempIdMap = new LinkedHashMap<>();
        Map<Long, List<CategoryDto>> tempParentIdMap = new LinkedHashMap<>();
        List<Category> thirdList = list(new QueryWrapper<Category>().eq("grade", 3).orderByAsc("sort"));
        List<Category> secondList = list(new QueryWrapper<Category>().eq("grade", 2).orderByAsc("sort"));
        List<Category> firstList = list(new QueryWrapper<Category>().eq("grade", 1).orderByAsc("sort"));
        for (Category category : secondList) {
            CategoryDto categoryDto = CategoryDto
                    .builder()
                    .id(category.getId())
                    .cname(category.getCname())
                    .grade(category.getGrade())
                    .parentId(category.getParentId())
                    .children(new LinkedList<>())
                    .build();
            List<CategoryDto> secondChild = tempParentIdMap.getOrDefault(category.getParentId(), new LinkedList<>());
            secondChild.add(categoryDto);
            tempParentIdMap.put(category.getParentId(), secondChild);
            tempIdMap.put(category.getId(), categoryDto);
        }
        for (Category category : thirdList) {
            Long parentId = category.getParentId();
            CategoryDto categoryDto = tempIdMap.get(parentId);
            categoryDto.getChildren().add(CategoryDto
                    .builder()
                    .id(category.getId())
                    .cname(category.getCname())
                    .grade(category.getGrade())
                    .parentId(category.getParentId())
                    .build());
        }
        for (Category category : firstList) {
            res.put(category.getId(), CategoryDto
                    .builder()
                    .id(category.getId())
                    .cname(category.getCname())
                    .grade(category.getGrade())
                    .parentId(category.getParentId())
                    .children(tempParentIdMap.get(category.getId()))
                    .build());
        }
        operations.set(RedisConstant.COMMODITY_CATEGORY, JSON.toJSONString(res));
        return res;
    }

    @Override
    public Category getInfoById(Long id) {
        return getOne(new QueryWrapper<Category>().select("id", "cname", "sort").eq("id", id));
    }

    @Override
    public void updateAndSyncCache(Category category) {
        stringRedisTemplate.delete(RedisConstant.COMMODITY_CATEGORY);
        updateById(category);
    }

    @Override
    public void assembleAndSave(List<CategoryDto> categoryDtos) {
        stringRedisTemplate.delete(RedisConstant.COMMODITY_CATEGORY);
        updateBatchById(assembleData(categoryDtos));

    }

    private List<Category> assembleData(List<CategoryDto> categoryDtos) {

        List<Category> categories = new LinkedList<>();
        AtomicInteger i = new AtomicInteger(1);
        for (CategoryDto categoryDto : categoryDtos) {
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setCname(categoryDto.getCname());
            category.setGrade(categoryDto.getGrade());
            category.setSort(i.getAndIncrement());
            category.setParentId(categoryDto.getParentId());
            categories.add(category);
            if (!CollectionUtils.isEmpty(categoryDto.getChildren())) {
                categories.addAll(assembleData(categoryDto.getChildren()));
            }
        }
        return categories;
    }


    // 一级目录
    // allData.stream().map(category -> {
    //     if (!category.getParentId().equals(0L)) {
    //     }
    // })
}
