package com.tplentiful.sys.pojo.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class MenuSaveAndModModel {

    @ApiModelProperty("菜单 id")
    private Long id;

    @ApiModelProperty("用户 id")
    private Long userId;

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("菜单路由")
    private String path;

    @ApiModelProperty("权限名称")
    private String[] perms;

    @ApiModelProperty("菜单等级")
    private Integer grade;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("元数据信息")
    private JSONObject meta;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("父菜单 ID")
    private Long parentId;
}
