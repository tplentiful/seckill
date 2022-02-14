package com.tplentiful.commodity.pojo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商品保存模型
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class SaveBrandModel {
    @NotBlank(message = "品牌名称不能为空")
    private String name;
    @NotNull(message = "品牌封面图片不能为空")
    private MultipartFile image;
    private Integer sort;
}
