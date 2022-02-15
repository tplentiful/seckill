package com.tplentiful.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.BrandCategoryDao;
import com.tplentiful.commodity.dao.BrandDao;
import com.tplentiful.commodity.dao.CategoryDao;
import com.tplentiful.commodity.pojo.po.Brand;
import com.tplentiful.commodity.pojo.po.BrandCategory;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.BrandCategoryService;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Service
public class BrandCategoryServiceImpl extends ServiceImpl<BrandCategoryDao, BrandCategory> implements BrandCategoryService {



    @Override
    public void addRelation(BrandCategory brandCategory) {
        BrandCategory brandCategoryObj = getOne(new QueryWrapper<BrandCategory>().eq("cid", brandCategory.getCid()).eq("bid", brandCategory.getBid()));
        if (!ObjectUtils.isEmpty(brandCategoryObj)) {
            throw new BizException("当前关系已存在");
        }
        save(brandCategory);
    }

    @Override
    public void delRelation(BrandCategory brandCategory) {
        remove(new QueryWrapper<BrandCategory>().eq("cid", brandCategory.getCid()).eq("bid", brandCategory.getBid()));
    }


}
