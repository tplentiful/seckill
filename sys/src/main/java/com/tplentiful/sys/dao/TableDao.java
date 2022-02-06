package com.tplentiful.sys.dao;

import com.tplentiful.sys.pojo.po.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Mapper
public interface TableDao {
    List<Table> selectOneByTableInfo(@Param("database") String database, @Param("tableName") String tableName);
}
