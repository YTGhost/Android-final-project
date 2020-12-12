package com.bjtu.androidbackend.controller;


import com.bjtu.androidbackend.model.Course;
import com.bjtu.androidbackend.service.ClasslistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户课程表模块")
@RestController
@RequestMapping("/classlist")
@CrossOrigin
public class ClasslistController {

    @Autowired
    ClasslistService classlistService;

    @ApiOperation(value = "添加课程")
    @PostMapping(value = "/add")
    @ResponseBody
    public Map<String, Object> addCourse(@RequestBody Course course) {
        Map<String, Object> map = new HashMap<>();
        List<Course> conflictList = classlistService.judgeConflict(course);
        if(!conflictList.isEmpty()) {
            map.put("code", 1);
        } else {
            map.put("code", 0);
            classlistService.addCourse(course);
        }
        map.put("data", "");
        map.put("msg", "");
        return map;
    }

    @ApiOperation(value = "加载课程")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public Map<String, Object> getList(@PathVariable(name = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Course> list = classlistService.getList(id);
        map.put("code", 0);
        map.put("data", list);
        map.put("msg", "");
        return map;
    }

    @ApiOperation(value = "删除课程")
    @PostMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> deleteCourse(@RequestBody Course course) {
        Map<String, Object> map = new HashMap<>();
        classlistService.deleteCourse(course);
        map.put("code", 0);
        map.put("data", "");
        map.put("msg", "");
        return map;
    }
}
