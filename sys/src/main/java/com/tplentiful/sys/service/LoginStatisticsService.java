package com.tplentiful.sys.service;

import com.tplentiful.sys.pojo.dto.LoginStatisticsDto;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 登录统计表 服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-02-25
 */
public interface LoginStatisticsService extends IService<LoginStatistics> {

    LoginStatisticsDto queryData(String year);

    int[] getDataList();

    Long getOnlineCount();

}
