package com.tplentiful.commodity.dao;

import com.tplentiful.commodity.pojo.po.AttrSpu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品属性和商品关联管理表 Mapper 接口
 * </p>
 *
 * @author tplentiful
 * @since 2022-03-07
 */
@Mapper
public interface AttrSpuDao extends BaseMapper<AttrSpu> {

}
