package com.tplentiful.commodity.service.impl;

import com.tplentiful.com.pojo.po.Category;
import com.tplentiful.com.dao.CategoryDao;
import com.tplentiful.com.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

}
