package com.bjtu.androidbackend.service;

import com.bjtu.androidbackend.model.Course;

import java.util.List;

public interface ClasslistService {

    public void addCourse(Course course);

    public List<Course> judgeConflict(Course course);

    public List<Course> getList(Integer id);

    public void deleteCourse(Course course);
}
