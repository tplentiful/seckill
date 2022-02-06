package com.tplentiful.sys;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.tplentiful.sys.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class SysApplicationTests {

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        String ss = "12345678!@#$%^";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
        log.info("加密后: {}", bCryptPasswordEncoder.encode(ss));;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testPassEncoder() {
        String database = "t_sys";
        String module = database.substring(2);
        FastAutoGenerator.create("jdbc:mysql://192.168.5.129:3306/" + database, "seckill", "U2FsdGVkX1/Hoj4x5seIpg36bGcu6WaeGp7m")
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
                    builder.addInclude("menu", "perm", "role", "role_perm", "user", "user_role"); // 设置需要生成的表名
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




}
