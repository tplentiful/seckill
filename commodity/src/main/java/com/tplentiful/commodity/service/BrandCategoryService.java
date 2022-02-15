package com.tplentiful.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.commodity.pojo.po.BrandCategory;
import com.tplentiful.common.utils.PageModel;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface BrandCategoryService extends IService<BrandCategory> {

    void addRelation(BrandCategory brandCategory);

    void delRelation(BrandCategory brandCategory);
}
