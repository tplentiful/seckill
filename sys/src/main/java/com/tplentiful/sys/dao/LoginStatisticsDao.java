package com.tplentiful.sys.dao;

import com.tplentiful.sys.pojo.po.DateList;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 登录统计表 Mapper 接口
 * </p>
 *
 * @author tplentiful
 * @since 2022-02-25
 */
@Mapper
public interface LoginStatisticsDao extends BaseMapper<LoginStatistics> {

    DateList selectDataList();
}
