package com.tplentiful.sys.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.sys.dao.LoginStatisticsDao;
import com.tplentiful.sys.pojo.dto.LoginStatisticsDto;
import com.tplentiful.sys.pojo.po.DateList;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.tplentiful.sys.service.LoginStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 登录统计表 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-02-25
 */
@Slf4j
@Service
public class LoginStatisticsServiceImpl extends ServiceImpl<LoginStatisticsDao, LoginStatistics> implements LoginStatisticsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginStatisticsDto queryData(String year) {
        DateTime startTime = DateUtil.parse(year + "-01-01");
        DateTime endTime = DateUtil.parse(Integer.parseInt(year) + 1 + "-01-01");
        List<LoginStatistics> res = list(new QueryWrapper<LoginStatistics>().ge("create_at", startTime).lt("create_at", endTime).orderByAsc("create_at"));
        LoginStatisticsDto loginStatisticsDto = new LoginStatisticsDto();
        if (CollectionUtils.isEmpty(res)) {
            return loginStatisticsDto;
        }
        List<Long> sortedLoginStatisticsList = res.stream().map(LoginStatistics::getCount).sorted().collect(Collectors.toList());
        List<LoginStatisticsDto.LoginStatisticsList> loginStatisticsDtoList = res.stream().map(loginStatistics -> {
            LoginStatisticsDto.LoginStatisticsList loginStatisticsList = new LoginStatisticsDto.LoginStatisticsList();
            loginStatisticsList.setCount(loginStatistics.getCount());
            loginStatisticsList.setDate(DateUtil.format(loginStatistics.getCreateAt(), "yyyy-MM-dd"));
            return loginStatisticsList;
        }).collect(Collectors.toList());
        loginStatisticsDto.setMin(sortedLoginStatisticsList.get(0));
        loginStatisticsDto.setMax( sortedLoginStatisticsList.get(sortedLoginStatisticsList.size() - 1));
        loginStatisticsDto.setDataList(loginStatisticsDtoList);
        return loginStatisticsDto;
    }

    @Override
    public int[] getDataList() {
        DateList dateList = this.baseMapper.selectDataList();
        int startYear = Integer.parseInt(DateUtil.format(dateList.getMinDate(), "yyyy"));
        int endYear = Integer.parseInt(DateUtil.format(dateList.getMaxDate(), "yyyy"));
        int[] arr = new int[endYear - startYear + 1];
        for (int i = startYear; i <= endYear; i++) {
            arr[i - startYear] = i;
        }
        return arr;
    }

    @Override
    public Long getOnlineCount() {
        Long onlineCount = stringRedisTemplate.execute((RedisCallback<Long>) conn -> conn.bitCount(RedisConstant.STATISTICS_ONLINE_COUNT.getBytes(StandardCharsets.UTF_8)));
        if (ObjectUtils.isEmpty(onlineCount)) {
            onlineCount = 0L;
        }
        return onlineCount;
    }
}
