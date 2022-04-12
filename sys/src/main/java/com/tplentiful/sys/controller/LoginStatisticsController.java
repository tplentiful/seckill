package com.tplentiful.sys.controller;


import com.tplentiful.common.utils.TR;
import com.tplentiful.sys.pojo.dto.LoginStatisticsDto;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.tplentiful.sys.service.LoginStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 登录统计表 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/loginStatistics")
public class LoginStatisticsController {


    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @GetMapping("/get/{year}")
    public TR<LoginStatisticsDto> getData(@PathVariable("year") String year) {
        return TR.ok("", loginStatisticsService.queryData(year));
    }


    @GetMapping("/getDateList")
    public TR<int[]> getDateList() {
        return  TR.ok("", loginStatisticsService.getDataList());
    }


    @GetMapping("/getOnlineCount")
    public TR<Long> getOnlineCount() {
        return TR.ok("", loginStatisticsService.getOnlineCount());
    }
}

