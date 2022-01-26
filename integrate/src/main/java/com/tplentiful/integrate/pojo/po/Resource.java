package com.tplentiful.integrate.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Data
@ApiModel(value = "Resource对象", description = "")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("下载资源的路径")
    private String urls;

    @ApiModelProperty("方法 1 下载 2 上传")
    private Integer method;

    @TableLogic(value = "1", delval = "0")
    @ApiModelProperty("是否有效")
    private Integer valid;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("创建时间")
    private Date createAt;


}
