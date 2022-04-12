package com.tplentiful.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.SpecDao;
import com.tplentiful.commodity.pojo.model.SpecSaveModel;
import com.tplentiful.commodity.pojo.po.Spec;
import com.tplentiful.commodity.service.SpecService;
import com.tplentiful.common.utils.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 规格参数表 spu 属性 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Service
public class SpecServiceImpl extends ServiceImpl<SpecDao, Spec> implements SpecService {

    @Override
    public IPage<Spec> queryPage(Long cid, PageModel pageModel) {
        QueryWrapper<Spec> spuQueryWrapper = new QueryWrapper<Spec>().orderByDesc("update_at");
        if (cid != null && cid != 0) {
            spuQueryWrapper.eq("cid", cid);
        }
        Object tempKey = pageModel.getKey();
        if (!ObjectUtils.isEmpty(tempKey)) {
            spuQueryWrapper.like("spec_name", tempKey);

            // -1 查询闲置的，就是还没有关联的
        }
        return page(new Page<>(pageModel.getPage(), pageModel.getLimit())
                , spuQueryWrapper);
    }

    @Override
    public void saveOne(SpecSaveModel specSaveModel) {
        Spec spec = new Spec();
        spec.setSpecName(specSaveModel.getSpecName());
        spec.setUnit(specSaveModel.getUnit());
        spec.setCid(specSaveModel.getCid());
        spec.setSearch(specSaveModel.getSearch());
        save(spec);
    }

    @Override
    public Spec getOneById(Long id) {
        return getById(id);
    }

    @Override
    public List<Spec> getAllData(String key, Long cid) {
        QueryWrapper<Spec> wrapper = new QueryWrapper<>();
        if (cid != null && cid != 0) wrapper.eq("cid", cid);
        if (StringUtils.hasText(key)) wrapper.like("spec_name", key);
        return list(wrapper);
    }


}
