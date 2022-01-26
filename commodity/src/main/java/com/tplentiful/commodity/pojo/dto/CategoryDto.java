package com.tplentiful.commodity.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.errorprone.annotations.NoAllocation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @ApiModelProperty("下级目录")
    private List<CategoryDto> children;
}
