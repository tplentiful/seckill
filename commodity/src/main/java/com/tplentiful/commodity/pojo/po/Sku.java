package com.tplentiful.commodity.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * spu 表
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Data
@ApiModel(value = "Sku对象", description = "spu 表")
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    @ApiModelProperty("SPU ID")
    private Long name;

    @ApiModelProperty("价格(分)")
    private Integer price;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("分类 ID")
    private Long cid;

    @ApiModelProperty("上架下架")
    private Boolean up;

    @ApiModelProperty("是否有效")
    private Boolean valid;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("创建时间")
    private Date createAt;


}
