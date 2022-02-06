package com.tplentiful.sys.service.impl;

import com.tplentiful.sys.dao.TableDao;
import com.tplentiful.sys.pojo.model.TableModel;
import com.tplentiful.sys.pojo.po.Table;
import com.tplentiful.sys.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Service("tableService")
public class TableServiceImpl implements TableService {
    @Autowired
    private TableDao tableDao;

    @Override
    public List<Table> getTableInfoByDatabaseAndTableName(TableModel tableModel) {
        List<Table> tables = tableDao.selectOneByTableInfo(tableModel.getDatabase(), tableModel.getTableName());
        if (CollectionUtils.isEmpty(tables)
                || CollectionUtils.isEmpty(tableModel.getExcludeColumn())) {
            return tables;
        }
        return tables.stream().filter(table -> !tableModel.getExcludeColumn().contains(table.getColumnName())).collect(Collectors.toList());
    }
}
