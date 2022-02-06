package com.tplentiful.sys.pojo.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class Table {

    @ApiModelProperty("列原位置")
    private String ordinalPosition;

    @ApiModelProperty("列名称")
    private String columnName;

    @ApiModelProperty("列注释")
    private String columnComment;
}
