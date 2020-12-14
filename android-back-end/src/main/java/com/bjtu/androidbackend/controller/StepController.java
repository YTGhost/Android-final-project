package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.Step;
import com.bjtu.androidbackend.service.AccountService;
import com.bjtu.androidbackend.util.JedisInstance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Api(tags = "计步模块")
@RestController
@RequestMapping("/step")
@CrossOrigin
public class StepController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "获取用户步数")
    @PostMapping(value = "/getStep")
    @ResponseBody
    public Map<String,Object> getAllSteps(@RequestBody Step step){
        String stepKey="user-step";
        Jedis jedis= JedisInstance.getInstance().getResource();

        //判断当前用户步数是否大于数据库存储步数，若是更新当前用户步数
        String userSteps=jedis.hget(stepKey,step.getNickname());
        if(userSteps == null || step.getSteps() > Integer.parseInt(userSteps)){
            jedis.hset(stepKey,step.getNickname(), String.valueOf(step.getSteps()));
            int expireTime=getRemainSecondsOneDay(new Date());
            jedis.expire(stepKey,expireTime);
        }


        //为所有步数排序
        Map<String,String> steps=jedis.hgetAll(stepKey);
        jedis.close();
        List<Map.Entry<String,String>> list=new ArrayList<>(steps.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return Integer.parseInt(o2.getValue())-Integer.parseInt(o1.getValue());
            }
        });
        //放入数据列表
        List<Step> data=new ArrayList<>();
        list.forEach((i)->{
            Step userStep = new Step();
            userStep.setNickname(i.getKey());
            userStep.setAvatarUrl(accountService.getAvatarByNickname(i.getKey()));
            if(i.getValue()!=null){
                userStep.setSteps(Integer.parseInt(i.getValue()));
            }else {
                userStep.setSteps(0);
            }
            data.add(userStep);
        });
        //返回数据
        Map<String,Object> res=new HashMap<>();
        res.put("code",0);
        res.put("data",data);
        res.put("msg","获取步数成功");
        return res;
    }

    public int getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

}
