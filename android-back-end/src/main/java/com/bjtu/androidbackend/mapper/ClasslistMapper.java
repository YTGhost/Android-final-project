package com.bjtu.androidbackend.mapper;

import com.bjtu.androidbackend.model.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClasslistMapper {

    @Insert("insert into course (id, day, startTime, endTime, courseName, room) values (#{id}, #{day}, #{startTime}, #{endTime}, #{courseName}, #{room})")
    public void addCourse(Course course);

    @Select("select * from course where id = #{id} and day = #{day} and endTime >= #{endTime} and startTime <= #{endTime} or id = #{id} and day = #{day} and startTime <= #{startTime} and endTime >= #{startTime} or id = #{id} and day = #{day} and startTime > #{startTime} and endTime < #{endTime}")
    public List<Course> judgeConflict(Course course);

    @Select("select * from course where id = #{id}")
    public List<Course> getList(Integer id);

    @Delete("delete from course where id = #{id} and day = #{day} and startTime = #{startTime}")
    public void deleteCourse(Course course);
}
