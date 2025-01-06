package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.dao.ScheduleDAO;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.model.Schedule;
import com.edu.duongdua.core.view.Scene_ManageSchedule;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.*;

public class ManageScheduleController extends Controller {

    public Scene_ManageSchedule manageScheduleView;

    public ManageScheduleController() {
        manageScheduleView = new Scene_ManageSchedule(this); // Truyền Controller vào View
        manageScheduleView.createManageSchedule(getAllActiveSchedule());
    }

    public Set<String> getClassName(String dayyyy) {
        Set<String> classNames = new HashSet<>();
        for (Classes classes : classesDao.getClassesInfo(1)) {
            classNames.add(classes.getClassName());
        }
        return classNames;
    }

    public List<Schedule> getAllActiveSchedule(){
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList = scheduleDAO.getAllSchedule();
        for(Schedule schedule : scheduleList){
            schedule.setClassName(classesDao.findByID(schedule.getClassId()).getClassName());
        }
        return scheduleList;
    }

    public Set<String> getClassesByDay(int day) {
        List<Schedule> allSchedules = getAllActiveSchedule();
        Set<String> uniqueClasses = new HashSet<>();
        for (Schedule schedule : allSchedules) {
            if (schedule.getDay().length() >= 4 && schedule.getDay().substring(4).equals(String.valueOf(day))) {
                String className = classesDao.findByID(schedule.getClassId()).getClassName();
                if (className != null) {
                    uniqueClasses.add(className);
                }
            }
        }
        return uniqueClasses;
    }

    public Schedule getScheduleByClassNameAndDay(String className, int day) {
        List<Schedule> schedules = getSchedulesByDay(day);
        for (Schedule schedule : schedules) {
            String scheduleClassName = classesDao.findByID(schedule.getClassId()).getClassName();
            if (scheduleClassName != null && scheduleClassName.equals(className)) {
                return schedule;
            }
        }
        return null;
    }

    public List<Schedule> getSchedulesByDay(int day) {
        List<Schedule> allSchedules = getAllActiveSchedule();
        List<Schedule> daySchedules = new ArrayList<>();
        for (Schedule schedule : allSchedules) {
            if (schedule.getDay().length() >= 4 && schedule.getDay().substring(4).equals(String.valueOf(day))) {
                daySchedules.add(schedule);
            }
        }
        return daySchedules;
    }

    public Set<String> getAvailableDays() {
        Set<String> allDays = new HashSet<>();
        for (int i = 2; i <= 7; i++) {
            allDays.add("Thứ " + String.valueOf(i));
        }
        return allDays;
    }

    public boolean addSchedule(String day, String className, String classRoom, String time) {
        Classes classObj = classesDao.findByName(className);
        int classId = classObj.getClassId();
        Schedule scheduleObj = new Schedule();
        scheduleObj.setClassId(classId);
        scheduleObj.setDay(day);
        scheduleObj.setClassRoom(classRoom);
        scheduleObj.setTime(time);

        return scheduleDAO.insertSchedule(scheduleObj);
    }

    public boolean deleteSchedule(String day, String classRoom, String time) {
        return scheduleDAO.deleteSchedule(day, classRoom, time);
    }
}
