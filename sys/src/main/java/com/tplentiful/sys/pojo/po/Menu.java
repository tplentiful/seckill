package com.tplentiful.sys.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@Data
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private String meta;

    @ApiModelProperty("菜单等级")
    private Integer grade;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("父菜单 ID")
    private Long parentId;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("创建时间")
    private Date createAt;


}
