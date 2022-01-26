package com.tplentiful.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.tplentiful.commodity.pojo.dto.CategoryDto;
import com.tplentiful.commodity.pojo.po.Category;
import com.tplentiful.commodity.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/

@SpringBootTest
@Slf4j
public class CommodityApplicationTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testGenerateCode() {
        String database = "com";
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
                    builder.addInclude("attr", "brand", "brand_category", "category", "sku", "spu", "spec"); // 设置需要生成的表名
                    builder.controllerBuilder().enableRestStyle();
                    builder.mapperBuilder().formatMapperFileName("%sDao");
                    builder.serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl");
                    builder.entityBuilder().idType(IdType.AUTO).enableLombok().enableRemoveIsPrefix();
                })
                .execute();
    }

    @Test
    public void testCategoryService() {
        Map<Long, CategoryDto> res = new HashMap<>();
        Map<Long, List<CategoryDto>> secondMap = new HashMap<>();
        Map<Long, CategoryDto> tempSecondMap = new HashMap<>();
        List<Category> allData = categoryService.list();
        Long parentId;
        for (Category allDatum : allData) {
            parentId = allDatum.getParentId();
            if (parentId.equals(0L)) {
                res.put(allDatum.getId(), CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build());
            } else if (allDatum.getGrade().equals(2)) {
                List<CategoryDto> tempList = secondMap.getOrDefault(parentId, new ArrayList<>());
                CategoryDto categoryDto = CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build();
                tempList.add(categoryDto);
                secondMap.put(parentId, tempList);
                tempSecondMap.put(categoryDto.getId(), categoryDto);
            }
        }
        for (Category allDatum : allData) {
            if (allDatum.getGrade().equals(3)) {
                parentId = allDatum.getParentId();
                List<CategoryDto> children = tempSecondMap.get(parentId).getChildren();
                children.add(CategoryDto.builder().id(allDatum.getId()).cname(allDatum.getCname()).children(new ArrayList<>()).build());
            }
        }
        for (Long key : res.keySet()) {
            CategoryDto categoryDto = res.get(key);
            categoryDto.setChildren( secondMap.get(key));
        }

    }
}

