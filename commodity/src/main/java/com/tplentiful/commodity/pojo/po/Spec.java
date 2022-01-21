package com.tplentiful.commodity.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 规格参数表 spu 属性
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Getter
@Setter
@ApiModel(value = "Spec对象", description = "规格参数表 spu 属性")
public class Spec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("分类 ID")
    private Long cid;

    @ApiModelProperty("spu 属性(规格参数)")
    private String specName;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("是否可检索")
    @TableField("is_search")
    private Boolean search;

    @ApiModelProperty("是否有效")
    private Boolean valid;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty("创建时间")
    private LocalDateTime createAt;


}
