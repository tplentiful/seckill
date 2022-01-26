package com.tplentiful.integrate.pojo.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class UploadResourceDto {
    private String msg;
    private List<UploadResourceItem> uploadResourceItems;

    @Data
    public static class UploadResourceItem {
        private String originName;
        private String url;
    }
}
