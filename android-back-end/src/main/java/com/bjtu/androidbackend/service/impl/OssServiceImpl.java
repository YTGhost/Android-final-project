package com.bjtu.androidbackend.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.bjtu.androidbackend.util.ConstantPropertiesUtils;
import com.bjtu.androidbackend.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service("ossService")
public class OssServiceImpl implements OssService {

    @Override
    public String uploadOssFile(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OssClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + fileName;
            // 获取当前日期
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date + "/" + fileName;
            // 调用OSS方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient
            ossClient.shutdown();
            // 拼接url
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
