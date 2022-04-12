package com.tplentiful.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.sys.pojo.model.ForgetPasswordModel;
import com.tplentiful.sys.pojo.model.ForgetPasswordValidModel;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.Role;
import com.tplentiful.sys.pojo.po.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
public interface UserService extends IService<User> {

    /**
     * 根据 email 查询全部权限
     *
     * @param email
     * @return
     */
    List<Perm> getPermsByEmail(String email);


    /**
     * 根据 email 查询全部角色
     *
     * @param email
     * @return
     */
    List<Role> getRolesByEmail(String email);


    User getUserByEmail(String email);

    User getUserById(Long id);

    List<User> queryUserById(Long id);

    void validCheckCode(ForgetPasswordValidModel model);

    void restPassword(ForgetPasswordModel model);
}
