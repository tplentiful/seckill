package com.tplentiful.sys.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.sys.dao.UserDao;
import com.tplentiful.sys.pojo.model.ForgetPasswordModel;
import com.tplentiful.sys.pojo.model.ForgetPasswordValidModel;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.Role;
import com.tplentiful.sys.pojo.po.User;
import com.tplentiful.sys.security.TpJwtUtil;
import com.tplentiful.sys.service.RoleService;
import com.tplentiful.sys.service.UserRoleService;
import com.tplentiful.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public User getUserById(Long id) {
        return getOne(new QueryWrapper<User>()
                .eq("id", id)
                .select("id", "avatar", "name", "email", "locked", "create_at"));
    }

    @Override
    public List<User> queryUserById(Long id) {
        List<User> users = list(new QueryWrapper<User>().select("id", "avatar", "name", "email", "locked", "update_at", "create_at"));
        if (!CollectionUtils.isEmpty(users)) {
            users = users.stream().filter(user -> !id.equals(user.getId())).collect(Collectors.toList());
        }
        return users;
    }

    @Override
    public void validCheckCode(ForgetPasswordValidModel model) {
        HashOperations<String, String, Object> operations = stringRedisTemplate.opsForHash();
        Object checkCode = operations.get(model.getEmail(), RedisConstant.EMAIL_CODE);
        if (checkCode == null || !checkCode.equals(model.getCheckCode())) {
            throw new BizException("验证码错误");
        }
        operations.delete(model.getEmail(), RedisConstant.EMAIL_CODE);
    }

    @Override
    public void restPassword(ForgetPasswordModel model) {
        String salt = RandomUtil.randomString(6);
        String realPassword = TpJwtUtil.bCryptPasswordEncoder.encode(model.getPassword() + salt);
        User user = new User();
        user.setEmail(model.getEmail());
        user.setPasswd(realPassword);
        user.setSalt(salt);
        this.baseMapper.restPassword(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return getOne(new QueryWrapper<User>()
                .eq("email", email)
                .select("id", "avatar", "name", "email", "locked", "create_at"));
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
