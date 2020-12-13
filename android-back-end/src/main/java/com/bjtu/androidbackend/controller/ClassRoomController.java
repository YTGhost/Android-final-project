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
        String building = classRoom.getBuilding();
        if(building.equals("逸夫")) {
            list.add("301");
            list.add("302");
            list.add("304");
            list.add("310");
            list.add("402");
            list.add("405");
            list.add("507");
            list.add("510");
        } else if (building.equals("思西")){
            list.add("102");
            list.add("101");
            list.add("204");
            list.add("205");
            list.add("301");
            list.add("303");
            list.add("304");
            list.add("306");
        } else if (building.equals("思源")) {
            list.add("107");
            list.add("108");
        } else {
            list.add("202");
            list.add("204");
        }
        map.put("code", 0);
        map.put("data", list);
        map.put("msg", "");
        return map;
    }
}
