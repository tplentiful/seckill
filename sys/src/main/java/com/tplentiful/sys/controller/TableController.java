package com.tplentiful.sys.controller;

import com.tplentiful.common.utils.TR;
import com.tplentiful.sys.pojo.model.TableModel;
import com.tplentiful.sys.pojo.po.Table;
import com.tplentiful.sys.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping("/select")
    public TR<List<Table>> getTableInfo(@RequestBody TableModel tableModel) {
        return TR.ok("", tableService.getTableInfoByDatabaseAndTableName(tableModel));
    }
}
