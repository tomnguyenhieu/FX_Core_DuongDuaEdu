package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.*;
import com.edu.duongdua.core.view.Scene_DashboardStudent;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class DashboardStudentController extends Controller {
    public DashboardStudentController(){
        countStudentAge();
        sceneDashboardStudent.setUpPieChart();
        caculateAvgStudentAge();
        countStudentByGender();
        sortTopStudent();
        countClassData();
        sceneDashboardStudent.setUpYearComboBox(getYears());
        sceneDashboardStudent.onActionListener(this::handleOnAction);
        sceneDashboardStudent.setUpLineChart();
        sceneDashboardStudent.setUpStudentData();
    }
    public Scene_DashboardStudent sceneDashboardStudent = new Scene_DashboardStudent();

    public List<String> getYears(){
        List<String> yearList = new ArrayList<>();
        for(Bill bill : billList){
            String year = bill.getTime().substring(3);
            if(!yearList.contains(year)){
                yearList.add(year);
            }
        }
        for(int i = 1; i < yearList.size(); i++){
            // Insertion sort
            String key = yearList.get(i);
            int j = i-1;

            while(j >= 0 && Integer.parseInt(yearList.get(j)) < Integer.parseInt(key)){
                yearList.set(j+1, yearList.get(j));
                j = j - 1;
            }
            yearList.set(j + 1, key);
        }
        return yearList;
    }

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
            if(student.getStatus().equals("Đang hoạt động")){
                if(student.getGender().equals("Nam")){
                    // Nếu học sinh là nam
                    maleCount++;
                }else{
                    femaleCount++;
                }
            }
        }
        sceneDashboardStudent.setMaleStudent(maleCount);
        sceneDashboardStudent.setFemaleStudent(femaleCount);
    }

    public List<List<Integer>> countTotalStudentByYear(String year){
        List<List<Integer>> dataList = new ArrayList<>();
        List<String> allMonth = new ArrayList<>(); // lưu trữ để kiểm tra tháng đã đếm hay chưa
        for(Bill bill : billList){
            if(bill.getType() == 4 && bill.getTime().substring(3).equals(year)){
                List<Integer> monthList = new ArrayList<>(); // lưu trữ tháng và số học sinh tháng đó
                String month = bill.getTime().substring(0,2);
                int totalStudent = countStudent(bill.getTime());
                if(!allMonth.contains(month)){
                    // Chưa có tháng vừa xử lí
                    allMonth.add(month);
                    monthList.add(Integer.parseInt(month));
                    monthList.add(totalStudent);
                    dataList.add(monthList);
                }
            }
        }

        // Insertion sort
        for(int i = 1; i < dataList.size(); i++){
            List<Integer> key = dataList.get(i);
            int j = i-1;
            while(j >= 0 && dataList.get(j).getFirst() > key.getFirst()){
                dataList.set(j+1, dataList.get(j));
                j = j - 1;
            }
            dataList.set(j + 1, key);
        }
        return dataList;
    }

    public int countStudent(String date){
        int totalStudent = 0;
        List<Integer> studentIdList = new ArrayList<>(); // Lưu id học sinh để không đếm lại
        for(Bill bill : billList){
            if(bill.getTime().equals(date) && bill.getType() == 4){
                int studentId = bill.getAccount_id();
                if(!studentIdList.contains(studentId)){
                    totalStudent++;
                    studentIdList.add(studentId);
                }
            }
        }
        return totalStudent;
    }

    public void sortTopStudent(){
        String latestMonth = lessonList.getLast().getTitle().substring(3);
        List<Account> topStudentInMonth = new ArrayList<Account>();
        int[] topStudentScore = new int[commentList.size() + 1]; // Lưu điểm tương ứng với top
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

    public void handleOnAction(ActionEvent event){
        String year = sceneDashboardStudent.yearComboBox.getValue().toString();
        sceneDashboardStudent.refreshLineChart(countTotalStudentByYear(year));
    }
}
