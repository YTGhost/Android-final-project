package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "课程实体类")
public class Course implements Serializable {

    @ApiModelProperty(name = "id", value = "用户Id", required = false)
    private Integer id;

    @ApiModelProperty(name = "day", value = "星期几", required = false)
    private Integer day;

    @ApiModelProperty(name = "startTime", value = "课程上课时间", required = false)
    private Integer startTime;

    @ApiModelProperty(name = "endTime", value = "课程下课时间", required = false)
    private Integer endTime;

    @ApiModelProperty(name = "courseName", value = "课程名", required = false)
    private String courseName;

    @ApiModelProperty(name = "room", value = "教室", required = false)
    private String room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", courseName='" + courseName + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
