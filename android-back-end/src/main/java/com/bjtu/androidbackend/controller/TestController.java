package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.Test;
import com.bjtu.androidbackend.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "系统测试模块")
@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @Autowired
    TestService testService;

    @ApiOperation(value = "测试负载均衡")
    @GetMapping(value = "/loadBalance")
    @ResponseBody
    public Map<String, Object> loadBalance() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", "");
        map.put("msg", "这里是6386");
        return map;
    }

    // Get示例
    @ApiOperation(value = "测试获取用户列表")
    @GetMapping(value = "/getList")
    @ResponseBody
    public Map<String, Object> getList() {
        List<Test> testList = testService.getList();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", testList);
        map.put("msg", "success");
        return map;
    }

    // Post示例
    @ApiOperation(value = "测试用户登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> Login(@RequestBody Test test) {
        List<Test> list = testService.login(test.getUsername());
        Map<String, Object> map = new HashMap<>();
        if(list.isEmpty()) {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "未找到用户名");
        } else {
            Test item = list.get(0);
            if(item.getPassword().equals(test.getPassword())) {
                map.put("code", 0);
                map.put("data", "");
                map.put("msg", "登录成功");
            } else {
                map.put("code", 2);
                map.put("data", "");
                map.put("msg", "密码错误");
            }
        }
        return map;
    }

}
