package com.tplentiful.common.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class TpFileUtil {

    public static final String NEW_LINE = FileUtil.getLineSeparator();
    public static final String DOMAIN = "https://tplentiful.bio/resource/";
    public static final String HOME_PATH = "/data/resource/";
    public static final File HOME = new File(HOME_PATH);

    static {
        if (!HOME.isDirectory() && HOME.isFile()) {
            throw new BizException("当前资源文件路径是一个文件" + HOME_PATH);
        }
        if (HOME.exists()) {
            log.info("资源文件目录加载成功");
        } else if (HOME.mkdirs()) {
            log.info("临时文件目录创建成功");
        } else {
            throw new BizException("临时文件目录创建失败");
        }
    }


    public static void downloadImage(String url, HttpServletResponse response) {
        try {
            URL requestUrl = new URL(url);
            ServletOutputStream outputStream = response.getOutputStream();
            DataInputStream imageStream = new DataInputStream(requestUrl.openStream());
            byte[] buffer = new byte[1024];
            int res;
            while ((res = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, res);
            }
            imageStream.close();
        } catch (IOException e) {
            log.info("文件下载失败: {}", url, e);
            throw new com.tplentiful.common.utils.BizException("文件下载失败");

        }

    }

    public static ResponseEntity<byte[]> downloadImages(List<String> urls) {
        ZipOutputStream zipOutputStream;
        File zipFile;
        try {
            zipFile = File.createTempFile(IdUtil.simpleUUID(), ".zip", HOME);
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (IOException e) {
            throw new BizException("压缩文件创建失败");
        }
        for (String url : urls) {
            try {
                URL requestUrl = new URL(url);
                URLConnection urlConnection = requestUrl.openConnection();
                String suffix = FileTypeUtil.getType(urlConnection.getInputStream());
                log.info("当前文件后缀为: {}", suffix);
                String filename = IdUtil.simpleUUID();
                DataInputStream imageStream = new DataInputStream(requestUrl.openStream());
                zipOutputStream.putNextEntry(new ZipEntry(filename + "." + suffix));
                byte[] buffer = new byte[1024];
                int res;
                while ((res = imageStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, res);
                }
                imageStream.close();
            } catch (IOException e) {
                log.error("文件下载失败: {}", url, e);
                throw new BizException("文件下载失败");
            }
        }
        try {
            zipOutputStream.close();
        } catch (IOException e) {
            log.error("压缩流关闭失败");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", zipFile.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(cn.hutool.core.io.FileUtil.readBytes(zipFile), headers, HttpStatus.CREATED);
        if (zipFile.delete()) {
            log.info("资源释放完成");
        }
        return responseEntity;
    }

}
