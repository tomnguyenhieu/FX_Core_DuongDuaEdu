package com.edu.duongdua.core.model;

public class Schedule extends Model{

    private int id;
    private String day;
    private int classId;
    private String className;
    private String classRoom;
    private String time;


    // Constructor mặc định (nếu cần)
    public Schedule() {}


    // Constructor có tham số
    public Schedule(String day, String className, String classRoom, String time) {
        this.day = day;
        this.className = className;
        this.classRoom = classRoom;
        this.time = time;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
