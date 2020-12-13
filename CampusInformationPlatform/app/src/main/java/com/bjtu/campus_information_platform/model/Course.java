package com.bjtu.campus_information_platform.model;


import java.io.Serializable;

public class Course implements Serializable {
    private int day;
    private int startTime;
    private int endTime;
    private String courseName;
    private String teacher;
    private String room;

    public Course(int day, int startTime, int endTime, String className, String teacher, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.teacher = teacher;
        this.room = room;
    }

    public Course(){

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }


    public String getClassName() {
        return courseName;
    }

    public void setClassName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
