package com.tplentiful.common.utils;

import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class PageModel {
    private Long page = 1L;
    private Integer limit = 8;
    private String key;
}
