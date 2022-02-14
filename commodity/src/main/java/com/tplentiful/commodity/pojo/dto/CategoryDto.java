package com.tplentiful.commodity.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @ApiModelProperty("分类名称")
    private String cname;

    @ApiModelProperty("层级")
    private Integer grade;

    @ApiModelProperty("父级Id")
    private Long parentId;

    @ApiModelProperty("下级目录")
    private List<CategoryDto> children;
}
