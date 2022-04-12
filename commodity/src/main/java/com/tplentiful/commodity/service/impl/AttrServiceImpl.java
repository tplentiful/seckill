package com.tplentiful.commodity.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.AttrDao;
import com.tplentiful.commodity.pojo.dto.AttrDto;
import com.tplentiful.commodity.pojo.model.AttrSaveAndModModel;
import com.tplentiful.commodity.pojo.po.Attr;
import com.tplentiful.commodity.service.AttrService;
import com.tplentiful.common.constant.StringConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrDao, Attr> implements AttrService {


    @Override
    public void saveOne(AttrSaveAndModModel model) {
        Attr hasAttr = getOne(new QueryWrapper<Attr>().eq("name", model.getName()));
        if (!ObjectUtils.isEmpty(hasAttr)) {
            throw new BizException("当前属性已经存在");
        }
        Attr attr = new Attr();
        attr.setName(model.getName());
        attr.setValue(ArrayUtil.join(model.getValues(), StringConstant.STRING_SPLIT));
        save(attr);
    }

    @Override
    public IPage<Attr> queryList(PageModel pageModel) {
        String key = pageModel.getKey();
        QueryWrapper<Attr> wrapper = new QueryWrapper<>();
        // 关键字查询
        if (StringUtils.hasText(key)) {
            wrapper.like("name", key);
        }
        return page(new Page<>(pageModel.getPage(), pageModel.getLimit()), wrapper);
    }

    @Override
    public AttrDto queryOne(Long id) {
        Attr attr = getOne(new QueryWrapper<Attr>().eq("id", id).eq("valid", 1));
        if (ObjectUtils.isEmpty(attr)) {
            throw new BizException("当前查询属性不存在");
        }
        AttrDto attrDto = new AttrDto();
        attrDto.setName(attr.getName());
        attrDto.setValues(attr.getValue().split(StringConstant.STRING_SPLIT));
        return attrDto;
    }

    @Override
    public void modOne(AttrSaveAndModModel model) {
        Attr attr = getById(model.getId());
        attr.setName(model.getName());
        attr.setValue(ArrayUtil.join(model.getValues(), StringConstant.STRING_SPLIT));
        attr.setValid(model.getValid());
        updateById(attr);
    }

    @Override
    public void deleteBatchByIds(Long[] id) {
        //TODO 结合着 spu 一起改变
    }

    @Override
    public List<Attr> getAllData(String key) {
        QueryWrapper<Attr> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(key)) {
            wrapper.like("name", key);
        }
        return list(wrapper);
    }


}
