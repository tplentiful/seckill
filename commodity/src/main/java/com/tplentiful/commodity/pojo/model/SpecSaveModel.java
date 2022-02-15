package com.tplentiful.commodity.pojo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class SpecSaveModel {
    @NotBlank(message = "商品参数名称不能为空")
    @ApiModelProperty("商品参数名称")
    private String specName;

    private Long cid;

    @NotBlank(message = "商品参数单位不能为空")
    @ApiModelProperty("商品参数单位")
    private String unit;

    @ApiModelProperty("是否可检索")
    private Boolean search;
}
