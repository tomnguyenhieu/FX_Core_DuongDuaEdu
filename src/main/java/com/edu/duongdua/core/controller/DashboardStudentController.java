package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.*;
import com.edu.duongdua.core.view.Scene_DashboardStudent;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DashboardStudentController extends Controller {
    public DashboardStudentController(){
        countStudentAge();
        sceneDashboardStudent.setUpPieChart();
        caculateAvgStudentAge();
        countStudentByGender();
        countTotalStudentByMonth();
        sortTopStudent();
        countClassData();
        sceneDashboardStudent.setUpLineChart();
        sceneDashboardStudent.setUpStudentData();
    }
    public Scene_DashboardStudent sceneDashboardStudent = new Scene_DashboardStudent();


    public void countStudentAge(){
        int ageUnder12Count = 0;
        int ageUnder22Count = 0;
        int ageOver22Count = 0;
        List<Account> studentList = accountDAO.getAllAccountByRole(4);
        for(Account student : studentList){
            int studentAge = student.getAge();
            if(studentAge <= 12){
                // Tuổi dưới 12
                ageUnder12Count++;
            }else if(studentAge <= 22){
                // Tuổi dưới 22
                ageUnder22Count++;
            }else {
                // Tuổi trên 22
                ageOver22Count++;
            }
        }
        sceneDashboardStudent.setAgeUnder12Count(ageUnder12Count);
        sceneDashboardStudent.setAgeUnder22Count(ageUnder22Count);
        sceneDashboardStudent.setAgeOver22Count(ageOver22Count);
    }

    public void caculateAvgStudentAge(){
        int studentCount = 0;
        int studentAgeSum = 0;
        int avgAge = 0;
        List<Account> studentList = accountDAO.getAllAccountByRole(4);
        // Tính tổng tuổi và số lượng học viên
        for(Account student : studentList){
            studentAgeSum += student.getAge();
            studentCount++;
        }
        avgAge = studentAgeSum / studentCount;
        sceneDashboardStudent.setAvgStudentAge(avgAge);
    }

    public void countStudentByGender(){
        int maleCount = 0;
        int femaleCount = 0;
        List<Account> studentList = accountDAO.getAllAccountByRole(4);
        for(Account student : studentList){
            if(student.getGender().equals("Nam")){
                // Nếu học sinh là nam
                maleCount++;
            }else{
                femaleCount++;
            }
        }
        sceneDashboardStudent.setMaleStudent(maleCount);
        sceneDashboardStudent.setFemaleStudent(femaleCount);
    }

    public void countTotalStudentByMonth(){
        List<String> monthArr = new ArrayList<>();
        // Với mỗi lesson, đếm học sinh dựa trên comment
        for(Lesson lesson : lessonList){
            String month = lesson.getTitle().substring(3);
            int total_student = 0;
            // Tạo list học sinh để kiểm tra trùng
            List<Integer> studentIdList = new ArrayList<>();
            for(Comment comment : commentList){
                // Với mỗi comment hay học sinh của lesson
                int studentId = comment.getStudentId();
                if(comment.getLessonId() == lesson.getId() && !studentIdList.contains(studentId)){
                    // Học sinh học lesson chưa được đếm
                    total_student++;
                    studentIdList.add(studentId);
                }
            }
            if(!monthArr.contains(month)){
                // Chưa có tháng của lesson hiện tại
                monthArr.add(lesson.getTitle().substring(3));
                sceneDashboardStudent.setDataSeries(month, total_student);
            }
        }
    }

    public void sortTopStudent(){
        String latestMonth = lessonList.getLast().getTitle().substring(3);
        List<Account> topStudentInMonth = new ArrayList<Account>();
        // Lưu điểm tương ứng với top
        int[] topStudentScore = new int[commentList.size() + 1];
        int top = 1;

        // Sắp xếp list comment theo điểm
        for(int i = 1; i < commentList.size(); i++){
            // Insertion sort
            Comment key = commentList.get(i);
            int j = i-1;

            while(j >= 0 && commentList.get(j).getScore() < key.getScore()){
                commentList.set(j+1, commentList.get(j));
                j = j - 1;
            }
            commentList.set(j + 1, key);
        }
        for(Comment comment : commentList){
            if(lessonDAO.getLessonInfoById(comment.getLessonId()).getTitle().substring(3).equals(latestMonth)){
                // Nếu comment trong tháng mới nhất
                // Thêm học sinh vào top
                topStudentInMonth.add(accountDAO.findStudentById(comment.getStudentId()));
                // Thêm điểm vào top
                topStudentScore[top] = comment.getScore();
                top++;
            }
        }
        top = 1;
        for(Account student : topStudentInMonth){
            // Lấy thông tin của học sinh tương ứng thứ hạng
            String name = student.getName();
            String className = classesDao.findByID(student.getClassId()).getClassName();
            String address = student.getAddress();
            int score = topStudentScore[top];
            sceneDashboardStudent.setUpTopStudent(top, name, className, address, score);
            top++;
            if(top == 11){
                break;
            }
        }
    }

    public void countClassData(){
        for(Classes classes : classesList){
            String name = classes.getClassName();
            int totalLesson = 0;
            int totalStudent = 0;

            // Đếm học sinh
            for(Account account : studentsList){
                if(account.getClassId() == classes.getClassId()){
                    totalStudent++;
                }
            }

            // Đếm số tiết
            for(Lesson lesson : lessonList){
                if(lesson.getClassId() == classes.getClassId()){
                    totalLesson++;
                }
            }
            sceneDashboardStudent.setUpClassData(name, totalStudent, totalLesson);
        }
    }
}
