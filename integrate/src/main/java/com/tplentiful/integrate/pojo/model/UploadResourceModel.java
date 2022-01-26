package com.tplentiful.integrate.pojo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class UploadResourceModel {
    private String path;
    private String token;
    private MultipartFile[] files;
}
