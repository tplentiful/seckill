package com.tplentiful.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.dao.PermDao;
import com.tplentiful.sys.service.PermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@Service
public class PermServiceImpl extends ServiceImpl<PermDao, Perm> implements PermService {

    @Override
    public List<Perm> listPermsById(Long id) {
        List<String> permsByIdStr = baseMapper.getPermsById(id).stream().map(Perm::getName).collect(Collectors.toList());
        List<Perm> permsAll = list(new QueryWrapper<Perm>().orderByAsc("sort"));
        Iterator<Perm> iterator = permsAll.iterator();
        // 拿到的 permsAll 是按照权限大小来排序的 遍历到当前用户拥有的权限就是最大的权限，以下权限都能使用
        while (iterator.hasNext()) {
            if (permsByIdStr.contains(iterator.next().getName())) {
                break;
            }
            iterator.remove();
        }
        return permsAll;
    }


    @Override
    public List<Perm> getPermsById(Long id) {
        return baseMapper.getPermsById(id);
    }
}
