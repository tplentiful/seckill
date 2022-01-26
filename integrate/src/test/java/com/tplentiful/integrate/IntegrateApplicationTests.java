package com.tplentiful.integrate;

import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@Slf4j
@SpringBootTest
class IntegrateApplicationTests {

    @Value("${source-rsa.pubKey}")
    private String pubKey;
    @Value("${source-rsa.priKey}")
    private String priKey;
    RSA rsa = new RSA(priKey, pubKey);

    @Test
    void contextLoads() {
    }

    @Test
    void testGenerateCode() {
        String database = "t_integrate";
        FastAutoGenerator.create("jdbc:mysql://192.168.5.129:3306/" + database, "seckill", "U2FsdGVkX1/Hoj4x5seIpg36bGcu6WaeGp7m")
                .globalConfig(builder -> {
                    builder.author("tplentiful") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Administrator\\Desktop\\resource"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tplentiful") // 设置父包名
                            .moduleName(database) // 设置父包模块名
                            .entity("pojo.po")
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\seckill\\" + database + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("resource"); // 设置需要生成的表名
                    builder.controllerBuilder().enableRestStyle();
                    builder.mapperBuilder().formatMapperFileName("%sDao");
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl");
                    builder.entityBuilder().idType(IdType.AUTO).enableLombok().enableRemoveIsPrefix();
                })
                .execute();
    }

    @Test
    public void testUpload() {
    }
}
