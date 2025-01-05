package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.Main;
import com.edu.duongdua.core.dao.*;
import com.edu.duongdua.core.model.*;

import java.util.List;

public class Controller
{
    public ClassesDAO classesDao = new ClassesDAO();;
    public AccountDAO accountDAO = new AccountDAO();
    public BillDAO billDAO = new BillDAO();
    public LessonDAO lessonDAO = new LessonDAO();
    public CommentDAO commentDAO = new CommentDAO();

    public List<Classes> classesList = classesDao.getAllClasses();
    public List<Account> teachersList = accountDAO.getAllAccountByRole(2);
    public List<Account> studentsList = accountDAO.getAllAccountByRole(4);
    public List<Lesson> lessonList = lessonDAO.getAllLessons();
    public List<Comment> commentList = commentDAO.getAllComments();
    public List<Bill> billList = billDAO.getAllBill();

    private static Controller instance;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Controller() {}

    public static Controller getInstance()
    {
        if (instance == null)
        {
            instance = new Controller();
        }
        return instance;
    }
}
