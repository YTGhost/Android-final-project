package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.Step;
import com.bjtu.androidbackend.util.JedisInstance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
    @ApiOperation(value = "获取用户步数")
    @PostMapping(value = "/getStep")
    @ResponseBody
    public Map<String,Object> getAllSteps(@RequestBody Step step){
        String stepKey="user-step";
        Jedis jedis= JedisInstance.getInstance().getResource();

        //更新当前用户步数
        jedis.hset(stepKey,step.getNickname(), String.valueOf(step.getSteps()));
        int expireTime=getRemainSecondsOneDay(new Date());
        jedis.expire(stepKey,expireTime);

        //为所有步数排序
        Map<String,String> steps=jedis.hgetAll(stepKey);
        List<Map.Entry<String,String>> list=new ArrayList<>(steps.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return Integer.parseInt(o2.getValue())-Integer.parseInt(o1.getValue());
            }
        });
        //返回数据
        Map<String,Object> res=new HashMap<>();
        res.put("msg","OK");
        res.put("num",list.size());
        res.put("data",list);
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
