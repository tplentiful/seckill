package com.tplentiful.integrate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tplentiful.integrate.pojo.dto.UploadResourceDto;
import com.tplentiful.integrate.pojo.model.DownloadResourceModel;
import com.tplentiful.integrate.pojo.model.ResourceModel;
import com.tplentiful.integrate.pojo.model.UploadResourceModel;
import com.tplentiful.integrate.pojo.po.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public interface ResourceService extends IService<Resource> {
    ResponseEntity<byte[]> downloadResource(DownloadResourceModel model);

    String generateToken(HttpServletRequest request);

    UploadResourceDto uploadResource(UploadResourceModel model);

    IPage<Resource> selectPage(ResourceModel model);

    void deleteAndRelease(Long[] ids);
}
