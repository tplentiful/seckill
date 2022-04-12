package com.tplentiful.sys.controller;


import com.tplentiful.common.utils.TR;
import com.tplentiful.sys.pojo.po.Perm;
import com.tplentiful.sys.pojo.po.User;
import com.tplentiful.sys.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tplentiful
 * @since 2022-01-31
 */
@RestController
@RequestMapping("/perm")
public class PermController {

    @Autowired
    private PermService permService;


    @GetMapping("/listPermsById/{id}")
    public TR<List<Perm>> getPermsById(@PathVariable("id") Long id) {
        List<Perm> permsByEmail = permService.listPermsById(id);
        return TR.ok("", permsByEmail);
    }

}

