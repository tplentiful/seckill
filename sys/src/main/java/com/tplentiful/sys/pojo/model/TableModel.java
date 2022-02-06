package com.tplentiful.sys.pojo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class TableModel {

    @ApiModelProperty("数据库名称")
    private String database;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("排除的列名称")
    private List<String> excludeColumn;
}
