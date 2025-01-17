package com.edu.duongdua.core.dao;

import com.edu.duongdua.core.db.Connector;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.model.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO extends Lesson
{
    private Connection conn;
    public LessonDAO()
    {
        this.conn = Connector.getConnection();
    }

    // Lấy tất cả lessons trong database
    public List<Lesson> getAllLessons()
    {
        List<Lesson> lessonList = new ArrayList<>();
        String sql = "SELECT * FROM lessons";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
                lessonList.add(lesson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lessonList;
    }

    // Tìm lesson bằng id của lesson đó
    public Lesson findByID(int lessonId)
    {
        Lesson lesson = new Lesson();
        String sql = "SELECT * FROM lessons WHERE id = " +lessonId;
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    public Lesson findByTitle(String title)
    {
        Lesson lesson = new Lesson();
        String sql = "SELECT * FROM lessons WHERE title = '"+title+"'";
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    // Thêm mới lesson vào trong database
    public boolean storeLesson(Lesson lesson)
    {
        String sql = "INSERT INTO lessons("
                + "class_id, title, content)"
                + " VALUES(?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, lesson.getClassId());
            ps.setString(2, lesson.getTitle());
            ps.setString(3, lesson.getContent());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    // Lấy thông tin của lesson (id, teacherName, className, title, content) bằng lesson id
    public Lesson getLessonInfoById(int lessonId)
    {
        Lesson lesson = new Lesson();
        String sql = "SELECT classes.id, accounts.name AS teacher_name, classes.name AS class_name, lessons.title, "
                + "lessons.content FROM classes JOIN accounts ON classes.teacher_id = accounts.id "
                + "JOIN lessons ON classes.id = lessons.class_id WHERE lessons.id = " +lessonId;
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                lesson.setId(rs.getInt("id"));
                lesson.setTeacherName(rs.getString("teacher_name"));
                lesson.setClassName(rs.getString("class_name"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    // Lấy các lessons theo lớp
    public List<Lesson> getLessonsByClassId(Classes classObj)
    {
        List<Lesson> lessonList = new ArrayList<>();
        String sql = "SELECT * FROM lessons WHERE class_id = " + classObj.getClassId();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
                lessonList.add(lesson);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lessonList;
    }
}
