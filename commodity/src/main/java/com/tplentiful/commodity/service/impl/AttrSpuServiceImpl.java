package com.tplentiful.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.dao.AttrSpuDao;
import com.tplentiful.commodity.pojo.po.AttrSpu;
import com.tplentiful.commodity.service.AttrSpuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性和商品关联管理表 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-03-07
 */
@Service("attrSpuService")
public class AttrSpuServiceImpl extends ServiceImpl<AttrSpuDao, AttrSpu> implements AttrSpuService {

}
