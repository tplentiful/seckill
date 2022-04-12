package com.tplentiful.sys;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.tplentiful.sys.dao.UserDao;
import com.tplentiful.sys.pojo.po.LoginStatistics;
import com.tplentiful.sys.service.LoginStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;

@Slf4j
@SpringBootTest
class SysApplicationTests {
    public static StringJoiner sj = null;
    Random random = new Random();

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginStatisticsService loginStatisticsService;

    public static void main(String[] args) throws ParseException {


    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testPassEncoder() {
        String database = "t_sys";
        String module = database.substring(2);
        FastAutoGenerator.create("jdbc:mysql://192.168.5.131:3306/" + database, "tplentiful", "$2b$10$caERe8c.QTSn1hCblawQjeXYoulEjAu/SPZDbwmOr7U9Nz1p.2Ev.")
                .globalConfig(builder -> {
                    builder.author("tplentiful") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Administrator\\Desktop\\resource"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tplentiful") // 设置父包名
                            .moduleName(module) // 设置父包模块名
                            .entity("pojo.po")
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\seckill\\" + module + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("login_statistics"); // 设置需要生成的表名
                    builder.controllerBuilder().enableRestStyle();
                    builder.mapperBuilder().formatMapperFileName("%sDao");
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl");
                    builder.entityBuilder().idType(IdType.AUTO).enableLombok().enableRemoveIsPrefix();
                })
                .execute();
    }

    @Test
    public void testUserDao() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
        String encode = bCryptPasswordEncoder.encode("123456");
        log.info("加密: {}", encode);
    }

    @Test
    public void testJWT() {

        Map<String, Object> map = new HashMap<>();
        map.put("email", "tplentiful@163.com");
        map.put("roles", "admin:sss");
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluOnNzcyIsImVtYWlsIjoidHBsZW50aWZ1bEAxNjMuY29tIiwiZXhwIjoxNjQzNzIzNjU0fQ.Dq4tA9otHISGrfcrjjj3Mgy-oSfm7CasrrLR5R83cL0";
        String jwtCode = JWT.create()
                .addPayloads(map)
                .setExpiresAt(DateUtil.offsetHour(new Date(), 6))
                .setKey(StrUtil.bytes("tplentiful")).sign();
        log.info("jwtCode: {}", jwtCode);
        JWT jwt = JWTUtil.parseToken(token);
        log.info("算法是: {}", jwt.getAlgorithm());
        log.info("头是: {}", jwt.getHeader());
        log.info("负载是: {}", jwt.getPayload());
        log.info("签名为: {}", jwt.getSigner());
    }

    /**
     * 添加登录个数伪数据
     */
    @Test
    public void addLoginCount() throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance();
        int year = 2019;
        Calendar calendar = Calendar.getInstance();
        // 设置当前年份
        List<LoginStatistics> arr = new ArrayList<>();
        for (int k = 2015; k <= 2021; k++) {
            calendar.set(Calendar.YEAR, k);
            for (int i = 0; i < 12; i++) {
                // 设置当前月份， 从 0 开始的
                calendar.set(Calendar.MONTH, i);
                int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int j = 1; j <= count; j++) {
                    StringJoiner sj = new StringJoiner("-");
                    sj.add(k + "").add(i + 1 < 10 ? "0" + (i + 1) : i + 1 + "").add(j + 1 < 10 ? "0" + j : j + "");
                    arr.add(generateData(dateFormat.parse(sj.toString())));
                }
            }
        }
        loginStatisticsService.saveBatch(arr);
        log.info("储存的数据长度: {}", arr.size());
        // loginStatistics.setUpdateAt(count);
        // loginStatistics.setCreateAt(count);
        // loginStatisticsService.save()
    }


    public LoginStatistics generateData(Date date) {
        Long count = (long) (random.nextInt(500) + 200);
        LoginStatistics loginStatistics = new LoginStatistics();
        loginStatistics.setCount(count);
        loginStatistics.setUpdateAt(date);
        loginStatistics.setCreateAt(date);
        return loginStatistics;
    }

    public static class SJInstance {
        public static StringJoiner getInstance() {
            if (sj == null) {
                sj = new StringJoiner("-");
            }
            return sj;

        }
    }

}
