package com.tplentiful.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.commodity.pojo.model.SpecSaveModel;
import com.tplentiful.commodity.pojo.po.Spec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.common.utils.PageModel;

import java.util.List;

/**
 * <p>
 * 规格参数表 spu 属性 服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
public interface SpecService extends IService<Spec> {

    IPage<Spec> queryPage(Long cid, PageModel pageModel);

    void saveOne(SpecSaveModel specSaveModel);

    Spec getOneById(Long id);

    List<Spec> getAllData(String key, Long cid);
}
