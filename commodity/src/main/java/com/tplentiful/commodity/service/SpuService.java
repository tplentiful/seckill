package com.tplentiful.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tplentiful.commodity.pojo.po.Spu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.common.utils.PageModel;

import java.util.List;

/**
 * <p>
 * spu 表 服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface SpuService extends IService<Spu> {

    IPage<Spu> queryPage(PageModel pageModel);
}
