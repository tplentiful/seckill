package com.tplentiful.integrate.utils;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.TpFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Component
public class FtpUtil {


    public static FTPClient createClient() {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            ftp.connect("47.96.5.71", 21);
            boolean login = ftp.login("ftpuser", "userftp123");
            if (!login) {
                throw new BizException("文件上传用户相关信息错误");
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            return ftp;
        } catch (IOException e) {
            log.error("ftp 客户端创建失败", e);
            throw new BizException("远程服务器无响应");
        }
    }

    public static String upload(MultipartFile file, String path) {
        String originalFilename = file.getOriginalFilename();
        try {
            FTPClient ftp = createClient();
            path = TpFileUtil.HOME_PATH + path;
            if (!ftp.changeWorkingDirectory(path)) {
                log.info("当前路径不存在，开始创建");
                if (ftp.makeDirectory(path)) {
                    log.info("创建成功， 切换工作目录");
                    ftp.changeWorkingDirectory(path);
                } else {
                    log.info("目录创建失败: {}", path);
                }
            }
            String filename = FileNameUtil.getPrefix(originalFilename) + "_" + IdUtil.simpleUUID() + "." + FileNameUtil.getSuffix(originalFilename);
            if (!ftp.storeFile(filename, file.getInputStream())) {
                log.info("文件上传失败");
            }
            return filename;
        } catch (IOException e) {
            log.error("文件上传失败: path: {},filename: {}", path, originalFilename, e);
            return "";
        }
    }
}
