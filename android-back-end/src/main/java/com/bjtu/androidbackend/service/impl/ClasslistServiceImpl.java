package com.bjtu.androidbackend.service.impl;

import com.bjtu.androidbackend.mapper.ClasslistMapper;
import com.bjtu.androidbackend.model.Course;
import com.bjtu.androidbackend.service.ClasslistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classlistService")
public class ClasslistServiceImpl implements ClasslistService {

    @Autowired
    private ClasslistMapper classlistMapper;

    @Override
    public void addCourse(Course course) {
        classlistMapper.addCourse(course);
    }

    @Override
    public List<Course> judgeConflict(Course course) {
        return classlistMapper.judgeConflict(course);
    }

    @Override
    public List<Course> getList(Integer id) {
        return classlistMapper.getList(id);
    }

    @Override
    public void deleteCourse(Course course) {
        classlistMapper.deleteCourse(course);
    }
}
