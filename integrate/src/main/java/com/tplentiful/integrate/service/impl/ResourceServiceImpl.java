package com.tplentiful.integrate.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.TpFileUtil;
import com.tplentiful.integrate.dao.ResourceDao;
import com.tplentiful.integrate.pojo.dto.UploadResourceDto;
import com.tplentiful.integrate.pojo.model.DownloadResourceModel;
import com.tplentiful.integrate.pojo.model.ResourceModel;
import com.tplentiful.integrate.pojo.model.UploadResourceModel;
import com.tplentiful.integrate.pojo.po.Resource;
import com.tplentiful.integrate.service.ResourceService;
import com.tplentiful.integrate.utils.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Service("resource")
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @Value("${source-rsa.pubKey}")
    private String pubKey;
    @Value("${source-rsa.priKey}")
    private String priKey;
    private final RSA rsa = new RSA(priKey, pubKey);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThreadPoolExecutor serviceExecutor;

    @Override
    public ResponseEntity<byte[]> downloadResource(DownloadResourceModel model) {
        String ip = validToken(model.getToken());
        List<String> urls = model.getUrls();
        // 记录存库
        Resource resource = new Resource();
        resource.setIp(ip);
        resource.setUrls(urls.toString());
        resource.setMethod(1);
        resource.setCreateAt(new Date());
        save(resource);
        return TpFileUtil.downloadImages(urls);
    }

    private String validToken(String token) {
        String ip;
        try {
            ip = StrUtil.str(rsa.decrypt(Base64.decode(token.replace(" ", "+")), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
        } catch (CryptoException e) {
            log.error("token 不存在", e);
            throw new BizException("token 不存在");
        }
        if (Boolean.FALSE.equals(stringRedisTemplate.opsForHash().hasKey(RedisConstant.RESOURCE_TOKEN, ip))) {
            throw new BizException("token 失效");
        }
        return ip;
    }

    @Override
    public String generateToken(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        HashOperations<String, String, Long> operations = stringRedisTemplate.opsForHash();
        log.info("获取 token: {}", ip);
        Long increment = operations.increment(RedisConstant.RESOURCE_TOKEN, ip, 1);
        if (increment > 6) {
            throw new BizException("token 获取过于频繁");
        }
        stringRedisTemplate.expire(RedisConstant.RESOURCE_TOKEN, 3, TimeUnit.MINUTES);
        return Base64.encode(rsa.encrypt(StrUtil.bytes(ip), KeyType.PublicKey));
    }

    @Override
    public UploadResourceDto uploadResource(UploadResourceModel model) {
        String ip = validToken(model.getToken());
        List<UploadResourceDto.UploadResourceItem> successRes = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        String filePath, filename, userPath = model.getPath();
        if (userPath.startsWith("/")) {
            userPath = userPath.substring(1);
        }
        for (MultipartFile file : model.getFiles()) {
            filename = file.getOriginalFilename();
            if (StringUtils.hasText(filePath = FtpUtil.upload(file, userPath))) {
                UploadResourceDto.UploadResourceItem uploadResourceItem = new UploadResourceDto.UploadResourceItem();
                uploadResourceItem.setOriginName(filename);
                uploadResourceItem.setUrl(TpFileUtil.DOMAIN + userPath + "/" + filePath);
                successRes.add(uploadResourceItem);
            } else {
                errorList.add(filename);
            }
        }
        UploadResourceDto uploadResourceDto = new UploadResourceDto();
        uploadResourceDto.setMsg("total: " + model.getFiles().length + ", success: " + successRes.size() + ", fail: " + errorList.size());
        uploadResourceDto.setUploadResourceItems(successRes);
        Resource resource = new Resource();
        resource.setIp(ip);
        resource.setUrls(successRes.stream().map(UploadResourceDto.UploadResourceItem::getUrl).collect(Collectors.toList()).toString());
        resource.setMethod(2);
        resource.setCreateAt(new Date());
        save(resource);
        return uploadResourceDto;
        // StringJoiner res = new StringJoiner(TpFileUtil.NEW_LINE, TpFileUtil.NEW_LINE +  + TpFileUtil.NEW_LINE, "");
        // for (String key : successRes.keySet()) {
        //     res.add(key + ": " + TpFileUtil.DOMAIN + userPath + "/" + successRes.get(key));
        // }
        // return res.toString();
    }

    @Override
    public IPage<Resource> selectPage(ResourceModel model) {
        String key;
        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>().orderByDesc("update_at");
        if (StringUtils.hasText(key = model.getKey())) {
            wrapper.eq("ip", key);
        }
        return page(new Page<>(model.getPage(), model.getLimit()), wrapper);
    }


}
