package com.tplentiful.sys.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tplentiful.sys.dao.UserDao;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service
public class TPUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里的 username 就是 email 不存在 username
        User user = userDao.selectOne(new QueryWrapper<User>().eq("email", username))   ;
        if (user == null) {
            throw new UsernameNotFoundException("当前用户不存在，请您注册!");
        }
        List<Perm> permList = userDao.getPermsByEmail(username);
        TPUser tpUser = new TPUser();
        tpUser.setId(user.getId());
        tpUser.setUsername(username);
        tpUser.setSalt(user.getSalt());
        tpUser.setPassword(user.getPasswd());
        Integer locked = user.getLocked();
        if (locked == 0) {
            tpUser.setValid(true);
        } else if (locked == 2) {
            tpUser.setLocked(true);
        }
        tpUser.setAuthorities(permList);
        return tpUser;
    }
}
