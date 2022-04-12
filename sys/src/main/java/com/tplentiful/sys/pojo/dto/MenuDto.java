package com.tplentiful.sys.pojo.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class MenuDto {

    private Long id;

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("菜单路由")
    private String path;

    @ApiModelProperty("权限名称")
    private String perms;

    @ApiModelProperty("元数据信息")
    private JSONObject meta;

    @ApiModelProperty("菜单等级")
    private Integer grade;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("父菜单 id")
    private Long parentId;

    @ApiModelProperty("子目录")
    private MenuDto child;
}
