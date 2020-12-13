package com.bjtu.androidbackend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "教室实体类")
public class ClassRoom implements Serializable {

    @ApiModelProperty(name = "building", value = "教学楼", required = false)
    private String building;

    @ApiModelProperty(name = "course", value = "时段", required = false)
    private String course;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "building='" + building + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
