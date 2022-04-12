package com.tplentiful.commodity.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品属性和商品关联管理表
 * </p>
 *
 * @author tplentiful
 * @since 2022-03-07
 */
@Getter
@Setter
@TableName("attr_spu")
@ApiModel(value = "AttrSpu对象", description = "商品属性和商品关联管理表")
public class AttrSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品id")
    private Long spuId;

    @ApiModelProperty("属性id")
    private Long attrId;

    @ApiModelProperty("属性值用 ' ,' 分割")
    private String value;


}
