package com.tplentiful.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.dto.AttrDto;
import com.tplentiful.commodity.pojo.model.AttrSaveAndModModel;
import com.tplentiful.commodity.pojo.po.Attr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.common.utils.PageModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface AttrService extends IService<Attr> {

    void saveOne(AttrSaveAndModModel model);

    IPage<Attr> queryList(PageModel pageModel);

    AttrDto queryOne(Long id);

    void modOne(AttrSaveAndModModel model);

    void deleteBatchByIds(Long[] id);

    List<Attr> getAllData(String key);
}
