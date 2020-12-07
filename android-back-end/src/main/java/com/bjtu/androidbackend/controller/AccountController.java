package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.Account;
import com.bjtu.androidbackend.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> Login(@ResponseBody Account account) {
        List<Account> list = accountService.login(account.getEmail());
        Map<String, Object> map = new HashMap<>();
        if(list.isEmpty()) {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "未找到用户名");
        } else {
            Account item = list.get(0);
            if(item.getPassword().equals())
        }
    }
}
