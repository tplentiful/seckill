package com.tplentiful.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.Role;
import com.tplentiful.sys.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    List<Role> getRolesByEmail(String email);

    List<Perm> getPermsByEmail(String email);

    void restPassword(User user);


}
