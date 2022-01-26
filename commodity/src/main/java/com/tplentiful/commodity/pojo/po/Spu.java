package com.tplentiful.commodity.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@ApiModel(value = "Spu对象", description = "spu 表")
public class Spu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("分类 ID")
    private Long cid;

    @ApiModelProperty("品牌 ID")
    private Long bid;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("重量(kg)")
    private Double weight;

    @ApiModelProperty("产品规格")
    private String spec;

    @ApiModelProperty("上架状态")
    private Boolean up;

    @ApiModelProperty("是否有效")
    private Boolean valid;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty("创建时间")
    private LocalDateTime createAt;


}
