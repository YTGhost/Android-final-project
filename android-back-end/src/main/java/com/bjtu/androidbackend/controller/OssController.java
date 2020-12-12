package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "Oss模块")
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String url = ossService.uploadOssFile(file);
        map.put("code", 0);
        map.put("data", url);
        map.put("msg", "");
        return map;
    }
}
