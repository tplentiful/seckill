package com.tplentiful.sys.controller;


import com.tplentiful.common.utils.TR;
import com.tplentiful.sys.pojo.model.ForgetUserModel;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.Role;
import com.tplentiful.sys.pojo.po.User;
import com.tplentiful.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/forget")
    public TR<Void> forget(@RequestBody ForgetUserModel model) {
        return TR.ok("密码已找回");
    }



    @GetMapping("/list/{id}")
    public TR<List<User>> list(@PathVariable("id") Long id) {
        List<User> users = userService.queryUserById(id);
        return TR.ok("用户列表加载成功", users);
    }


    @GetMapping("/getByEmail/{email}")
    public TR<User> getOneByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return TR.ok("用户信息获取成功", user);
    }

    @GetMapping("/getById/{id}")
    public TR<User> getOneById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return TR.ok("用户信息获取成功", user);
    }


    @GetMapping("/getPermsByEmail/{email}")
    public TR<List<Perm>> getPermsByEmail(@PathVariable("email") String email) {
        List<Perm> permsByEmail = userService.getPermsByEmail(email);
        return TR.ok("权限菜单获取成功", permsByEmail);
    }


    @GetMapping("/getRolesByEmail/{email}")
    public TR<List<Role>> getRolesByEmail(@PathVariable("email") String email) {
        List<Role> roles = userService.getRolesByEmail(email);
        return TR.ok("用户角色获取成功", roles);
    }


}

