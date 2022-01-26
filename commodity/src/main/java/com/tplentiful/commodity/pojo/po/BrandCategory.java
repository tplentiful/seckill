package com.tplentiful.commodity.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Data
@TableName("brand_category")
@ApiModel(value = "BrandCategory对象", description = "")
public class BrandCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌 ID")
    private Long bid;

    @ApiModelProperty("类目 ID")
    private Long cid;


}
