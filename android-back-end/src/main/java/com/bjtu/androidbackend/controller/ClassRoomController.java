package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.ClassRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "空闲教室模块")
@RestController
@RequestMapping("/queryClass")
@CrossOrigin
public class ClassRoomController {

    @ApiOperation(value = "查询空闲教室")
    @PostMapping(value = "/freeroom")
    @ResponseBody
    public Map<String, Object> getFreeroom(@RequestBody ClassRoom classRoom) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("301");
        list.add("310");
        list.add("204");
        map.put("code", 0);
        map.put("data", list);
        map.put("msg", "");
        return map;
    }
}
