package com.tplentiful.integrate.pojo.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class DownloadResourceModel {
    private List<String> urls;
    private String token;
}
