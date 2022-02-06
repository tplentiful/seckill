package com.tplentiful.sys.service;

import com.tplentiful.sys.pojo.model.TableModel;
import com.tplentiful.sys.pojo.po.Table;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public interface TableService {
    /**
     * 根据数据库和表名称获取表字段
     * @param tableModel
     * @return
     */
    List<Table> getTableInfoByDatabaseAndTableName(TableModel tableModel);
}
