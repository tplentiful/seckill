package com.tplentiful.integrate.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tplentiful.common.constant.RedisConstant;
import com.tplentiful.common.utils.BizException;
import com.tplentiful.common.utils.DownloadUtil;
import com.tplentiful.integrate.dao.ResourceDao;
import com.tplentiful.integrate.pojo.model.ResourceModel;
import com.tplentiful.integrate.pojo.po.Resource;
import com.tplentiful.integrate.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<byte[]> downloadResource(ResourceModel model) {
        String ip;
        try {
            ip = StrUtil.str(rsa.decrypt(Base64.decode(model.getToken().replace(" ", "+")), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
        } catch (CryptoException e) {
            throw new BizException("ip 错误");
        }
        if (Boolean.FALSE.equals(stringRedisTemplate.opsForSet().isMember(RedisConstant.RESOURCE_KEY, ip))) {
            throw new BizException("ip 失效");
        }
        log.info("{} : 验证成功", ip);
        String[] urls = model.getUrls();
        // 记录存库
        Resource resource = new Resource();
        resource.setIp(ip);
        resource.setUrls(Arrays.toString(urls));
        resource.setCreateAt(new Date());
        save(resource);

        return DownloadUtil.downloadImages(urls);
    }

    @Override
    public String generateToken(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        log.info("获取 token: {}", ip);
        stringRedisTemplate.opsForSet().add(RedisConstant.RESOURCE_KEY, ip);
        stringRedisTemplate.expire(RedisConstant.RESOURCE_KEY, 12, TimeUnit.MINUTES);
        return Base64.encode(rsa.encrypt(StrUtil.bytes(ip), KeyType.PublicKey));
    }


}
