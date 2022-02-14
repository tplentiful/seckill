package com.tplentiful.commodity.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.commodity.cosntant.CommonConstant;
import com.tplentiful.commodity.dao.BrandDao;
import com.tplentiful.commodity.pojo.model.SaveBrandModel;
import com.tplentiful.commodity.pojo.po.Brand;
import com.tplentiful.commodity.service.BrandService;
import com.tplentiful.commodity.utils.FtpUtil;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.PageModel;
import com.tplentiful.common.utils.TpFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Slf4j
@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Override
    public IPage<Brand> queryPage(Integer firstname, PageModel pageModel) {
        String key = pageModel.getKey();
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(key)) {
            if (firstname == 1) {
                wrapper.eq("first_name", key);
            } else {
                wrapper.like("name", key);
            }
        }
        return page(new Page<>(pageModel.getPage(), pageModel.getLimit()), wrapper);
    }

    @Override
    public void saveBrand(SaveBrandModel brandModel) {
        String firstName = PinyinUtil.getFirstLetter(brandModel.getName(), "").substring(0, 1);
        Brand brand = new Brand();
        brand.setFirstName(firstName.toUpperCase());
        brand.setSort(brandModel.getSort());
        brand.setName(brandModel.getName());
        String upload = FtpUtil.upload(brandModel.getImage(), CommonConstant.BRAND_PREFIX);
        if (!StringUtils.hasText(upload)) {
            throw new BizException("文件上传失败");
        }
        brand.setImage(TpFileUtil.DOMAIN + CommonConstant.BRAND_PREFIX + "/" + upload);
        save(brand);
    }

    @Override
    public void deleteBatchById(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<String> brandImageUrl = listByIds(idList).stream().map(Brand::getImage).collect(Collectors.toList());
        FtpUtil.delete(brandImageUrl);
        removeBatchByIds(idList);
    }

}
