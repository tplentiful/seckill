package com.tplentiful.sys.pojo.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class MenuListDto {

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

    @ApiModelProperty("父菜单 id")
    private Long parentId;

    @ApiModelProperty("子目录")
    private List<MenuListDto> children;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("创建时间")
    private Date createAt;
}
