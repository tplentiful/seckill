package com.tplentiful.integrate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tplentiful.common.utils.TR;
import com.tplentiful.integrate.pojo.dto.UploadResourceDto;
import com.tplentiful.integrate.pojo.model.DownloadResourceModel;
import com.tplentiful.integrate.pojo.model.ResourceModel;
import com.tplentiful.integrate.pojo.model.UploadResourceModel;
import com.tplentiful.integrate.pojo.po.Resource;
import com.tplentiful.integrate.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list")
    public TR<IPage<Resource>> list(ResourceModel params) {
        return TR.ok("资源列表获取成功", resourceService.selectPage(params));
    }

    @PostMapping("/delete")
    public TR<Void> delete(@RequestBody Long[] ids) {
        resourceService.deleteAndRelease(ids);
        return TR.ok("删除成功: " + Arrays.toString(ids));
    }

    @GetMapping("/token")
    public TR<String> getToken(HttpServletRequest request) {
        return TR.ok("token 获取成功", resourceService.generateToken(request));
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadNetworkResource(@RequestBody DownloadResourceModel model) {
        return resourceService.downloadResource(model);
    }

    @PostMapping("/upload")
    public TR<UploadResourceDto> upload(UploadResourceModel model) {
        return TR.ok("资源上传成功", resourceService.uploadResource(model));
    }


}
