package com.bjtu.androidbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    public String uploadOssFile(MultipartFile file);
}
