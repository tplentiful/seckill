package com.tplentiful.integrate;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

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
        FastAutoGenerator.create("jdbc:mysql://192.168.5.131:3306/" + database, "tplentiful", "$2b$10$caERe8c.QTSn1hCblawQjeXYoulEjAu/SPZDbwmOr7U9Nz1p.2Ev.")
                .globalConfig(builder -> {
                    builder.author("tplentiful") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Administrator\\Desktop\\resource"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tplentiful") // 设置父包名
                            .moduleName(database.substring(2)) // 设置父包模块名
                            .entity("pojo.po")
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\seckill\\" + database + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("email_suffix"); // 设置需要生成的表名
                    builder.controllerBuilder().enableRestStyle();
                    builder.mapperBuilder().formatMapperFileName("%sDao");
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl");
                    builder.entityBuilder().idType(IdType.AUTO).enableLombok().enableRemoveIsPrefix();
                })
                .execute();
    }

    @Test
    public void testUpload() {
        List<String> strings = JSON.parseObject("[https://tplentiful.bio/resource/asdc/logo_a7a65dcb72c74d91938a7ff13adffc2a.png, https://tplentiful.bio/resource/asdc/logo1_ad7ec5b951094eb2bf638e8e74141b8c.png, https://tplentiful.bio/resource/asdc/微信图片_20210612180730_副本_6bcecc5192b946139271f9d39af45269.jpg, https://tplentiful.bio/resource/asdc/logo_983ad4e954904679a8710d6077dac81d.png, https://tplentiful.bio/resource/asdc/logo1_2c998420e87c488ba3e96877a87ec5aa.png]", new TypeReference<List<String>>() {
        });
        log.info("结果: {}", strings);
    }

    public static void main(String[] args) {
        log.info("{}", SecureUtil.sha256().digestHex("tplentiful"));
    }
}
