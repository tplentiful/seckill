package com.tplentiful.sys.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2022-01-31
 */
@Data
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("后台管理用户名")
    private String name;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("后台管理用户密码")
    private String passwd;

    @ApiModelProperty("后台管理用户盐值")
    private String salt;

    @ApiModelProperty("状态 -1 锁定 0 删除 1 正常")
    private Integer locked;

    @ApiModelProperty("更新时间")
    private Date updateAt;

    @ApiModelProperty("创建时间")
    private Date createAt;


}
