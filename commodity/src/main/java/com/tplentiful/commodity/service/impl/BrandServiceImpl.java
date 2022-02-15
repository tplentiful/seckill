package com.tplentiful.commodity.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.cosntant.CommonConstant;
import com.tplentiful.commodity.dao.BrandCategoryDao;
import com.tplentiful.commodity.dao.BrandDao;
import com.tplentiful.commodity.dao.CategoryDao;
import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.model.SaveBrandModel;
import com.tplentiful.commodity.pojo.po.Brand;
import com.tplentiful.commodity.pojo.po.BrandCategory;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.BrandService;
import com.tplentiful.commodity.utils.FtpUtil;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TpFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
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
 * @since 2022-01-21
 */
@Slf4j
@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Autowired
    private BrandCategoryDao brandCategoryDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public IPage<Brand> queryPage(Integer firstname, PageModel pageModel) {
        String key = pageModel.getKey();
        QueryWrapper<Brand> wrapper = new QueryWrapper<Brand>().orderByAsc("sort");
        if (StringUtils.hasText(key)) {
            if (firstname == 1) {
                wrapper.eq("first_name", key);
            } else {
                wrapper.like("name", key);
            }
        }
        return page(new Page<>(pageModel.getPage(), pageModel.getLimit()), wrapper);
    }

    @Override
    public void saveBrand(SaveBrandModel brandModel) {
        String firstName = PinyinUtil.getFirstLetter(brandModel.getName(), "").substring(0, 1);
        Brand brand = new Brand();
        brand.setFirstName(firstName.toUpperCase());
        brand.setSort(brandModel.getSort());
        brand.setName(brandModel.getName());
        String upload = FtpUtil.upload(brandModel.getImage(), CommonConstant.BRAND_PREFIX);
        if (!StringUtils.hasText(upload)) {
            throw new BizException("文件上传失败");
        }
        brand.setImage(TpFileUtil.DOMAIN + CommonConstant.BRAND_PREFIX + "/" + upload);
        save(brand);
    }

    @Override
    public void deleteBatchById(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<String> brandImageUrl = listByIds(idList).stream().map(Brand::getImage).collect(Collectors.toList());
        FtpUtil.delete(brandImageUrl);
        brandCategoryDao.delete(new QueryWrapper<BrandCategory>().in("bid", idList));
        removeBatchByIds(idList);
    }

    @Override
    public Map<Long, CategoryDto> getCategories(Long id) {
        List<Long> cids = brandCategoryDao.selectList(new QueryWrapper<BrandCategory>().eq("bid", id)).stream().map(BrandCategory::getCid).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cids)) {
            return null;
        }
        List<Category> thirdCategoryList = categoryDao.selectList(new QueryWrapper<Category>().in("id", cids).orderByAsc("sort"));
        List<Long> secondIds = thirdCategoryList.stream().map(Category::getParentId).collect(Collectors.toList());
        List<Category> secondCategoryList = categoryDao.selectList(new QueryWrapper<Category>().in("id", secondIds).orderByAsc("sort"));
        List<Long> firstIds = secondCategoryList.stream().map(Category::getParentId).collect(Collectors.toList());
        List<Category> firstCategoryList = categoryDao.selectList(new QueryWrapper<Category>().in("id", firstIds).orderByAsc("sort"));
        Map<Long, CategoryDto> tempCategoryList = new LinkedHashMap<>();
        Map<Long, List<CategoryDto>> secondCategoryDtos = new LinkedHashMap<>();
        Map<Long, CategoryDto> firstCategoryDtos = new LinkedHashMap<>();
        for (Category category : secondCategoryList) {
            CategoryDto categoryDto = new CategoryDto();
            Long tempParentId = category.getParentId();
            categoryDto.setId(category.getId());
            categoryDto.setCname(category.getCname());
            categoryDto.setGrade(category.getGrade());
            categoryDto.setParentId(tempParentId);
            categoryDto.setChildren(new LinkedList<>());
            tempCategoryList.put(categoryDto.getId(), categoryDto);
            List<CategoryDto> tempList = secondCategoryDtos.getOrDefault(tempParentId, new LinkedList<>());
            tempList.add(categoryDto);
            secondCategoryDtos.put(tempParentId, tempList);
        }
        for (Category category : firstCategoryList) {
            CategoryDto categoryDto = new CategoryDto();
            Long tempId = category.getId();
            categoryDto.setId(tempId);
            categoryDto.setCname(category.getCname());
            categoryDto.setGrade(category.getGrade());
            categoryDto.setParentId(category.getParentId());
            categoryDto.setChildren(secondCategoryDtos.get(tempId));
            firstCategoryDtos.put(tempId, categoryDto);
        }
        for (Category category : thirdCategoryList) {
            CategoryDto categoryDto = new CategoryDto();
            Long parentId = category.getParentId();
            categoryDto.setId(category.getId());
            categoryDto.setCname(category.getCname());
            categoryDto.setGrade(category.getGrade());
            categoryDto.setParentId(parentId);
            tempCategoryList.get(parentId).getChildren().add(categoryDto);
        }
        return firstCategoryDtos;
    }

}
