package com.tplentiful.sys.pojo.po;

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
 * @since 2022-01-31
 */
@Data
@TableName("role_perm")
@ApiModel(value = "RolePerm对象", description = "")
public class RolePerm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色 ID")
    private Long roleId;

    @ApiModelProperty("权限 ID")
    private Long permId;


}
