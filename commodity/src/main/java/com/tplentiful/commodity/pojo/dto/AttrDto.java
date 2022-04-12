package com.tplentiful.commodity.pojo.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class AttrDto {

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("默认属性值")
    private String[] values;
}
