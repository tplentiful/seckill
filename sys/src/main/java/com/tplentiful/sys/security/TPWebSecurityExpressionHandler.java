package com.tplentiful.sys.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tplentiful.sys.dao.PermDao;
import com.tplentiful.sys.pojo.po.Perm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service
public class TPWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    private PermDao permDao;

    private RoleHierarchy roleHierarchy;

    public TPWebSecurityExpressionHandler(@Autowired PermDao permDao) {
        log.debug("初始化 TpWebSecurityExpressionHandler");
        this.permDao = permDao;
        this.roleHierarchy = initRoleHierarchy();
    }

    @Override
    public RoleHierarchy getRoleHierarchy() {
        return roleHierarchy;
    }

    @Override
    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public RoleHierarchy initRoleHierarchy() {
        List<Perm> perms = permDao.selectList(new QueryWrapper<Perm>().select("name").orderByAsc("sort"));
        StringJoiner sj = new StringJoiner(" > ");
        for (Perm perm : perms) {
            sj.add(perm.getName());
        }
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        log.debug("初始化 role 层次关系");
        roleHierarchy.setHierarchy(sj.toString());
        log.debug("初始化成功");
        return roleHierarchy;
    }
}
