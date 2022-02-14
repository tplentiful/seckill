package com.tplentiful.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.model.SaveBrandModel;
import com.tplentiful.commodity.pojo.po.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.common.utils.PageModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface BrandService extends IService<Brand> {

    IPage<Brand> queryPage(Integer firstname, PageModel pageModel);

    void saveBrand(SaveBrandModel brand);

    void deleteBatchById(Long[] ids);
}
