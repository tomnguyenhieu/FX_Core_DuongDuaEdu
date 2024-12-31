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
    public ScheduleDAO()
    {
        this.conn = Connector.getConnection();
    }

    // Lấy danh sách ngày học từ bảng schedule
    public List<Schedule> getDaySchedule() {
        List<Schedule> days = new ArrayList<>();
        String sql = "SELECT DISTINCT day FROM schedule ORDER BY RIGHT(day, 1)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Schedule _schedule = new Schedule();
                _schedule.setDay(rs.getString("day"));
                days.add(_schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn các ngày học: " + e.getMessage(), e);
        }
        return days;
    }

    public List<Schedule> getAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedule";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

    public void storeSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule (class_id, day, time, classroom) VALUES (?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, schedule.getClassId());
            ps.setString(2, schedule.getDay());
            ps.setString(3, schedule.getTime());
            ps.setString(4, schedule.getClassRoom());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Xử lý lỗi
            System.err.println("Error storing schedule: " + e.getMessage());
        }
    }

//    // Hàm xóa lịch học theo ID
//    public void deleteSchedule(int scheduleId) {
//        String sql = "DELETE FROM schedule WHERE id = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, scheduleId);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            // Xử lý lỗi
//            System.err.println("Error deleting schedule: " + e.getMessage());
//        }
//    }

//    public List<Schedule> findByClassId(int classId) {
//        List<Schedule> schedules = new ArrayList<>();
//        String sql = "SELECT * FROM schedule WHERE class_id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, classId);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Schedule schedule = new Schedule();
//                    schedule.setDay(rs.getString("day"));
//                    schedule.setClassId(rs.getInt("class_id"));
//                    schedule.setClassRoom(rs.getString("classroom"));
//                    schedule.setTime(rs.getString("time"));
//                    schedules.add(schedule);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error retrieving schedules by class ID: " + e.getMessage(), e);
//        }
//        return schedules;
//    }

    // Hàm xóa lịch học theo ID
    public boolean deleteSchedule(int scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có ít nhất một bản ghi bị xóa
        } catch (SQLException e) {
            // Xử lý lỗi
            System.err.println("Error deleting schedule: " + e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }


}

