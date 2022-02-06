package com.tplentiful.integrate.dao;

import com.tplentiful.integrate.pojo.po.EmailSuffix;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-29
 */
@Mapper
public interface EmailDao extends BaseMapper<EmailSuffix> {

}
