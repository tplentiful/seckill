package com.tplentiful.sys.pojo.dto;

import com.tplentiful.sys.pojo.po.LoginStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class LoginStatisticsDto {
    @ApiModelProperty("最小值")
    private Long min;

    @ApiModelProperty("最大值")
    private Long max;

    @ApiModelProperty("数据列表")
    private List<LoginStatisticsList> dataList;

    @Data
    public static class LoginStatisticsList {
        @ApiModelProperty("用户登录个数")
        private Long count;

        @ApiModelProperty("时间")
        private String date;
    }



}
