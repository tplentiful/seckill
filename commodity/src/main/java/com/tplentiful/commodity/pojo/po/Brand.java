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
 *
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Data
@ApiModel(value = "Brand对象", description = "")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("品牌名称")
    private String name;

    @ApiModelProperty("首字母")
    private String firstName;

    @ApiModelProperty("品牌图片")
    private String image;

    @ApiModelProperty("排序规则")
    private Integer sort;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty("创建时间")
    private LocalDateTime createAt;


}
