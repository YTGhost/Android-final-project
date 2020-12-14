package com.bjtu.androidbackend.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.bjtu.androidbackend.model.Account;
import com.bjtu.androidbackend.service.AccountService;
import com.bjtu.androidbackend.util.JedisInstance;
import com.bjtu.androidbackend.util.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private MailService mailService;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> Login(@RequestBody Account account) {
        List<Account> list = accountService.loginByEmail(account.getEmail());
        Map<String, Object> map = new HashMap<>();
        if(list.isEmpty()) {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "未找到邮箱");
        } else {
            Account item = list.get(0);
            BCrypt.Result result = BCrypt.verifyer().verify(account.getPassword().toCharArray(), item.getPassword());
            if(result.verified) {
                // 登录时如果发现时新用户，就可以设置为老用户了，下次再登录时，发过去的isNew就是0了
                if(item.getIsNew() == 1) {
                    accountService.newToOld(item.getId());
                }
                map.put("code", 0);
                map.put("data", item);
                map.put("msg", "登录成功");
            } else {
                map.put("code", 2);
                map.put("data", "");
                map.put("msg", "密码错误");
            }
        }
        return map;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    @ResponseBody
    public Map<String, Object> Register(@RequestBody Account account) {
        List<Account> list1 = accountService.loginByEmail(account.getEmail());
        List<Account> list2 = accountService.loginByNickname(account.getNickname());
        Map<String, Object> map = new HashMap<>();
        if(list1.isEmpty() && list2.isEmpty()) {
            String code = account.getCode();
            Jedis jedis = JedisInstance.getInstance().getResource();
            // 验证成功
            if(code.equals(jedis.get(account.getEmail()))) {
                account.setPassword(BCrypt.withDefaults().hashToString(10, account.getPassword().toCharArray()));
                account.setAvatarUrl("https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png");
                account.setBackgroundUrl("https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg");
                // 注册时设置为新用户
                account.setIsNew(1);
                accountService.register(account);
                map.put("code", 0);
                map.put("data", "");
                map.put("msg", "注册成功");
            } else {    // 验证失败
                map.put("code", 2);
                map.put("data", "");
                map.put("msg", "验证码错误或已过期");
            }
            jedis.close();
        } else {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "邮箱或用户名已被注册");
        }
        return map;
    }

    @ApiOperation(value = "注册发送邮件验证码")
    @GetMapping(value = "/getRegisterCode/{email}")
    @ResponseBody
    public Map<String, Object> getRegisterCode(@PathVariable(name = "email") String email) {
        Map<String, Object> map = new HashMap<>();
        List<Account> list = accountService.loginByEmail(email);
        if(!list.isEmpty()) {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "邮箱已被注册");
            return map;
        }
        Random random = new Random();
        StringBuilder verification = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            verification.append(random.nextInt(10));
        }
        mailService.sendMail(email, "请查收验证码", "您的验证码为：" + verification.toString() + "，请在5分钟内验证。");
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.setex(email, 300, verification.toString());
        jedis.close();
        map.put("code", 0);
        map.put("data", verification.toString());
        map.put("msg", "验证码发送成功");
        return map;
    }

    @ApiOperation(value = "忘记密码发送邮件验证码")
    @GetMapping(value = "/getForgetCode/{email}")
    @ResponseBody
    public Map<String, Object> getForgetCode(@PathVariable(name = "email") String email) {
        Map<String, Object> map = new HashMap<>();
        List<Account> list = accountService.loginByEmail(email);
        if(list.isEmpty()) {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "邮箱不存在");
            return map;
        }
        Random random = new Random();
        StringBuilder verification = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            verification.append(random.nextInt(10));
        }
        mailService.sendMail(email, "请查收验证码", "您的验证码为：" + verification.toString() + "，请在5分钟内验证。");
        Jedis jedis = JedisInstance.getInstance().getResource();
        jedis.setex(email, 300, verification.toString());
        jedis.close();
        map.put("code", 0);
        map.put("data", verification.toString());
        map.put("msg", "验证码发送成功");
        return map;
    }

    @ApiOperation(value = "用户忘记密码")
    @PostMapping(value = "/forget")
    @ResponseBody
    public Map<String, Object> Forget(@RequestBody Account account) {
        List<Account> list = accountService.loginByEmail(account.getEmail());
        Map<String, Object> map = new HashMap<>();
        if(!list.isEmpty()) {
            String code = account.getCode();
            Jedis jedis = JedisInstance.getInstance().getResource();
            // 验证成功
            if(code.equals(jedis.get(account.getEmail()))) {
                account.setPassword(BCrypt.withDefaults().hashToString(10, account.getPassword().toCharArray()));
                accountService.forget(account);
                map.put("code", 0);
                map.put("data", "");
                map.put("msg", "修改密码成功");
            } else {    // 验证失败
                map.put("code", 2);
                map.put("data", "");
                map.put("msg", "验证码错误或已过期");
            }
            jedis.close();
        } else {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "邮箱不存在");
        }
        return map;
    }

    @ApiOperation(value = "验证邮箱唯一性")
    @GetMapping(value = "/judgeEmail/{email}")
    @ResponseBody
    public Map<String, Object> judgeEmail(@PathVariable(name = "email") String email) {
        List<Account> list = accountService.loginByEmail(email);
        Map<String, Object> map = new HashMap<>();
        if(list.isEmpty()) {
            map.put("code", 0);
            map.put("data", "");
            map.put("msg", "邮箱是唯一的");
        } else {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "邮箱已经被注册");
        }
        return map;
    }

    @ApiOperation(value = "验证用户名唯一性")
    @GetMapping(value = "/judgeNickname/{nickname}")
    @ResponseBody
    public Map<String, Object> judgeNickname(@PathVariable(name = "nickname") String nickname) {
        List<Account> list = accountService.loginByNickname(nickname);
        Map<String, Object> map = new HashMap<>();
        if(list.isEmpty()) {
            map.put("code", 0);
            map.put("data", "");
            map.put("msg", "用户名是唯一的");
        } else {
            map.put("code", 1);
            map.put("data", "");
            map.put("msg", "用户名已经被注册");
        }
        return map;
    }

    @ApiOperation(value = "更新头像")
    @PostMapping(value = "/changeAvatar")
    @ResponseBody
    public Map<String, Object> changeAvatar(@RequestBody Account account) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(account);
        accountService.changeAvatar(account);
        map.put("code", 0);
        map.put("data", "");
        map.put("msg", "");
        return map;
    }

    @ApiOperation(value = "更新背景图")
    @PostMapping(value = "/changeBackground")
    @ResponseBody
    public Map<String, Object> changeBackground(@RequestBody Account account) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(account);
        accountService.changeBackground(account);
        map.put("code", 0);
        map.put("data", "");
        map.put("msg", "");
        return map;
    }
}
