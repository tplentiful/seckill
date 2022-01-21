package com.tplentiful.integrate.controller;

import com.tplentiful.common.utils.TR;
import com.tplentiful.integrate.pojo.model.ResourceModel;
import com.tplentiful.integrate.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadNetworkResource(ResourceModel model) {
        return resourceService.downloadResource(model);
    }

    @GetMapping("/token")
    public TR<String> getToken(HttpServletRequest request) {
        return TR.ok("token 获取成功", resourceService.generateToken(request));
    }
}
