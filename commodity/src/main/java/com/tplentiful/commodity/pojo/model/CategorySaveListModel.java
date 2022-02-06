package com.tplentiful.commodity.pojo.model;

import com.tplentiful.commodity.pojo.dto.CategoryDto;
import lombok.Data;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class CategorySaveListModel {
        private List<CategoryDto> categoryDtos;
}
