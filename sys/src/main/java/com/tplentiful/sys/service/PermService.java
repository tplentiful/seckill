package com.tplentiful.sys.service;

import com.tplentiful.sys.pojo.po.Perm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
public interface PermService extends IService<Perm> {

    List<Perm> listPermsById(Long id);

    List<Perm> getPermsById(Long id);
}
