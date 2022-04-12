package com.tplentiful.sys.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 登录统计表
 * </p>
 *
 * @author tplentiful
 * @since 2022-02-25
 */
@Data
@TableName("login_statistics")
@ApiModel(value = "LoginStatistics对象", description = "登录统计表")
public class LoginStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("登录个数")
    private Long count;

    @ApiModelProperty("修改时间")
    private Date updateAt;

    @ApiModelProperty("登陆时间")
    private Date createAt;


}
