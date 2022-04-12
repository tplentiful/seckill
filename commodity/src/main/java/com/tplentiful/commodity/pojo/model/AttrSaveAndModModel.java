package com.tplentiful.commodity.pojo.model;

import com.tplentiful.common.validtor.ModValidator;
import com.tplentiful.common.validtor.SaveValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
@ApiModel("商品属性储存数据对象")
public class AttrSaveAndModModel {

    @ApiModelProperty("id")
    @NotNull(message = "属性缺失唯一标识", groups = {ModValidator.class})
    private Long id;

    @ApiModelProperty("商品属性名称")
    @NotBlank(message = "商品名称不为空", groups = {ModValidator.class, SaveValidator.class})
    private String name;

    @ApiModelProperty("商品属性默认值")
    @NotNull(message = "属性默认值不能为空", groups = {ModValidator.class, SaveValidator.class})
    private String[] values;

    @ApiModelProperty("商品属性是否有效")
    private Boolean valid = Boolean.TRUE;


}
