package com.bjtu.androidbackend.controller;

import com.bjtu.androidbackend.model.Hole;
import com.bjtu.androidbackend.service.HoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "树洞模块")
@RestController
@RequestMapping("/hole")
@CrossOrigin
public class HoleController {

    @Autowired
    HoleService holeService;

    @ApiOperation(value = "添加树洞")
    @PostMapping(value = "/addHole")
    @ResponseBody
    public Map<String, Object> addHole(@RequestBody Hole hole) {
        Map<String, Object> map = new HashMap<>();
        holeService.addHole(hole);
        map.put("code", 0);
        map.put("data", "");
        map.put("msg", "添加树洞成功");
        return map;
    }

    @ApiOperation(value = "刷新树洞")
    @GetMapping(value = "/holeRefresh/{id}")
    @ResponseBody
    public Map<String, Object> holeRefresh(@PathVariable(name = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Hole> list = holeService.holeRefresh(id);
        if(list.isEmpty()) {
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        map.put("data", list);
        map.put("msg", "");
        return map;
    }

    @ApiOperation(value = "加载树洞")
    @GetMapping(value = "/holeLoadMore/{id}")
    @ResponseBody
    public Map<String, Object> holeLoadMore(@PathVariable(name = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Hole> list = holeService.holeLoadMore(id);
        if(list.isEmpty()) {
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        map.put("data", list);
        map.put("msg", "");
        return map;
    }

}
