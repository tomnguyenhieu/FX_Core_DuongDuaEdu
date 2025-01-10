package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.dao.ScheduleDAO;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.model.Schedule;
import com.edu.duongdua.core.view.Scene_ManageSchedule;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class ManageScheduleController extends Controller implements EventHandler {

    public Scene_ManageSchedule manageScheduleView;

    public ManageScheduleController() {
        manageScheduleView = new Scene_ManageSchedule();
        manageScheduleView.createManageSchedule(getAllActiveSchedule(), this);
        setUpSchedule();
    }

    public void setUpSchedule () {
        for (int i = 2; i <= 7; i++){
            Set<String> className = getClassesByDay(i);
            manageScheduleView.createComboBox(i, className, this);
        }
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

    public void refreshScene () {
        ManageScheduleController manageScheduleController = new ManageScheduleController();
        ((BorderPane) manageScheduleView.getAnchorPane().getParent()).setCenter(manageScheduleController.manageScheduleView.getAnchorPane());
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "classNameCB":
                String selectedClass = ((ComboBox<String>)event.getSource()).getValue();
                VBox vBox1 =((VBox) ((ComboBox<?>) event.getSource()).getParent().getParent().getParent());
                Label label = (Label) vBox1.getChildren().getFirst();
                int day = Integer.parseInt(label.getText().substring(4));

                if (selectedClass != null) {
                    Schedule schedule = getScheduleByClassNameAndDay(selectedClass, day);
                    if (schedule != null) {
                        manageScheduleView.updateClassDetails(schedule.getClassRoom(), schedule.getTime(), day);
                    } else {
                        manageScheduleView.resetClassDetails();
                    }
                } else {
                    manageScheduleView.resetClassDetails();
                }

                break;
            case "dayCB":
                String selectedDay = ((ComboBox<String>)event.getSource()).getValue();
                if (selectedDay != null) {
                    Set<String> availableClasses = getClassName(selectedDay);
                    manageScheduleView.refreshData(availableClasses);
                }
                break;
            case "confirmAddBtn":
                String days = manageScheduleView.cbDay.getValue();
                String className = manageScheduleView.cbClassName.getValue();
                String classRoom = manageScheduleView.inputClassRoom.getText();
                String time = manageScheduleView.inputTime.getText();

                if (days == null || className == null || classRoom == null || time == null) {
                    manageScheduleView.showAlert("Kiểm tra lại thông tin!", false);
                } else {
                    if (!manageScheduleView.isValidTimeFormat(time)){
                        manageScheduleView.showAlert("Thời gian không hợp lệ! Vui lòng nhập theo định dạng: 14h00-17h00.", false);
                    } else {
                        boolean success = addSchedule(days, className, classRoom, time);
                        if (success) {
                            manageScheduleView.showAlert( "Thêm lịch học thành công!", true);
                            manageScheduleView.modalStage.close();
                        } else {
                            manageScheduleView.showAlert("Thêm lịch học thất bại!", false);
                        }
                    }
                }
                refreshScene();
                break;
            case "dayComboBox":
                String selectDay = ((ComboBox<String>)event.getSource()).getValue().substring(4);
                if (selectDay != null) {
                    Set<String> classNames = getClassesByDay(Integer.parseInt(selectDay));
                    manageScheduleView.loadData(classNames);
                }
                break;
            case "classNameComboBox":
                String selectedClasses = manageScheduleView.comboBoxClassName.getValue();
                String selectedDays = manageScheduleView.comboBoxDay.getValue().substring(4);
                if (selectedClasses != null && selectedDays != null) {
                    Schedule schedule = getScheduleByClassNameAndDay(selectedClasses, Integer.parseInt(selectedDays));
                    if (schedule != null) {
                        manageScheduleView.lblRoom.setText("Phòng học: " + schedule.getClassRoom());
                        manageScheduleView.lblTime.setText("Giờ học: " + schedule.getTime());
                    } else {
                        manageScheduleView.lblRoom.setText("Phòng học: ");
                        manageScheduleView.lblTime.setText("Giờ học: ");
                    }
                }
                break;
            case "confirmDeleteBtn":
                String dayss = manageScheduleView.comboBoxDay.getValue();
                String classRoomss = manageScheduleView.lblRoom.getText().substring(11);
                String timess = manageScheduleView.lblTime.getText().substring(9);

                if (dayss == null || classRoomss == null || timess == null) {
                    manageScheduleView.showAlert("Kiểm tra lại thông tin!", false);
                } else {
                    if (!manageScheduleView.isValidTimeFormat(timess)){
                        manageScheduleView.showAlert("Thời gian không hợp lệ! Vui lòng nhập theo định dạng: 14h00-17h00.", false);
                    } else {
                        boolean success = deleteSchedule(dayss, classRoomss, timess);
                        if (success) {
                            manageScheduleView.showAlert( "Thêm lịch học thành công!", true);
                            manageScheduleView.modalStage.close();
                        } else {
                            manageScheduleView.showAlert("Thêm lịch học thất bại!", false);
                        }
                    }
                }
                refreshScene();
                break;
        }
    }
}
