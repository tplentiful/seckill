package com.tplentiful.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.SpuDao;
import com.tplentiful.commodity.pojo.po.Spu;
import com.tplentiful.commodity.service.SpuService;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.PageModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu 表 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuDao, Spu> implements SpuService {

    @Override
    public IPage<Spu> queryPage(PageModel pageModel) {
        Object tempKey = pageModel.getKey();
        QueryWrapper<Spu> spuQueryWrapper = new QueryWrapper<>();
        if (tempKey == null) {
            // -1 查询闲置的，就是还没有关联的
            spuQueryWrapper.eq("cid", -1);
        } else {
            spuQueryWrapper.eq("cid", tempKey);
        }
        return page(new Page<>(pageModel.getPage(), pageModel.getLimit())
                , spuQueryWrapper);

    }
}
