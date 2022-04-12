package com.tplentiful.sys;

import com.tplentiful.sys.service.LoginStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class DaoTest extends SysApplicationTests {
    @Autowired
    private LoginStatisticsService loginStatisticsService;

    @Test
    public void loginStatisticsDaoTest() {
        loginStatisticsService.getDataList();

    }
}
