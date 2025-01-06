package com.edu.duongdua.core.dao;

import com.edu.duongdua.core.db.Connector;
import com.edu.duongdua.core.model.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO extends Schedule {

    private Connection conn;

    public ScheduleDAO() {
        this.conn = Connector.getConnection();
    }

    public List<Schedule> getAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedule";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Schedule schedule = new Schedule();
                schedule.setDay(rs.getString("day"));
                schedule.setClassId(rs.getInt("class_id"));
                schedule.setClassRoom(rs.getString("classroom"));
                schedule.setTime(rs.getString("time"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn lịch học: " + e.getMessage(), e);
        }
        return schedules;
    }

    public boolean insertSchedule(Schedule schedule) {
        String query = "INSERT INTO schedule (class_id, day, classroom, time) VALUES ( ?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, schedule.getClassId());
            ps.setString(2, schedule.getDay());
            ps.setString(3, schedule.getClassRoom());
            ps.setString(4, schedule.getTime());
//            int rowsAffected = ps.executeUpdate();
            return ps.executeUpdate() > 0; // Check if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn lịch học: " + e.getMessage(), e);
        }
    }

    public boolean deleteSchedule(String day, String classRoom, String time) {
        String query = "DELETE FROM schedule WHERE day = ? AND classroom = ? AND time = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1,day);
            ps.setString(2, classRoom);
            ps.setString(3, time);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
