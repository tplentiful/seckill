package com.tplentiful.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.sys.dao.UserDao;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.Role;
import com.tplentiful.sys.pojo.po.User;
import com.tplentiful.sys.service.RoleService;
import com.tplentiful.sys.service.UserRoleService;
import com.tplentiful.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public User getUserById(Long id) {
        return getOne(new QueryWrapper<User>()
                .eq("id", id)
                .select("id", "name", "passwd", "email", "salt", "locked"));
    }

    @Override
    public List<User> queryUserById(Long id) {
        List<User> users = list(new QueryWrapper<User>().select("id", "name", "email", "locked", "update_at", "create_at"));
        if (!CollectionUtils.isEmpty(users)) {
            users = users.stream().filter(user -> !id.equals(user.getId())).collect(Collectors.toList());
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        return getOne(new QueryWrapper<User>()
                .eq("email", email)
                .select("id", "name", "passwd", "email", "salt", "locked"));
    }


    @Override
    public List<Perm> getPermsByEmail(String email) {
        return baseMapper.getPermsByEmail(email);
    }

    @Override
    public List<Role> getRolesByEmail(String email) {
        return baseMapper.getRolesByEmail(email);
    }
}
