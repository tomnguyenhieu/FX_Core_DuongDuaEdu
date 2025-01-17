package com.edu.duongdua.core.dao;

import com.edu.duongdua.core.db.Connector;
import com.edu.duongdua.core.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAO extends Bill {
    private Connection conn;
    public BillDAO()
    {
        this.conn = Connector.getConnection();
    }

    public List<Bill> getAllBill()
    {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY RIGHT(time, 4), LEFT(time, 2)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAccount_id(rs.getInt("account_id"));
                bill.setName(rs.getString("name"));
                bill.setCreated_at(rs.getString("create_at"));
                bill.setUpdated_at(rs.getString("updated_at"));
                bill.setTime(rs.getString("time"));
                bill.setQuantity(rs.getInt("quantity"));
                bill.setPrice(rs.getInt("price"));
                bill.setTotal_price(rs.getInt("total_price"));
                bill.setStatus(rs.getInt("status") == 2 ? "Đã thanh toán" : "Chưa thanh toán");
                bill.setType(rs.getInt("type"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bills;
    }
    public void addBill(Bill bill){
        String sql = "INSERT INTO bills (account_id, name, create_at, updated_at, time, quantity, price, total_price, status, type)" +
                "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bill.getAccount_id());
            ps.setString(2, bill.getName());
            ps.setString(3, bill.getCreated_at());
            ps.setString(4, bill.getUpdated_at());
            ps.setString(5, bill.getTime());
            ps.setInt(6, bill.getQuantity());
            ps.setInt(7, bill.getPrice());
            ps.setInt(8, bill.getTotal_price());
            ps.setInt(9, 1);
            ps.setInt(10, bill.getType());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBill(Bill bill) {
        String sql = "UPDATE bills SET account_id = ?, name = ?, create_at = ?, updated_at = ?, time = ?, quantity = ?, price = ?, total_price = ?, status = ?, type = ? WHERE id = ?";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bill.getAccount_id());
            ps.setString(2, bill.getName());
            ps.setString(3, bill.getCreated_at());
            ps.setString(4, bill.getUpdated_at());
            ps.setString(5, bill.getTime());
            ps.setInt(6, bill.getQuantity());
            ps.setInt(7, bill.getPrice());
            ps.setInt(8, bill.getTotal_price());
            ps.setInt(9, bill.getStatus().equals("Đã thanh toán") ? 2 : 1);
            ps.setInt(10, bill.getType());
            ps.setInt(11, bill.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Bill findBillById(int id){
        String sql = "SELECT * FROM bills WHERE id = '"+id+"'";
        PreparedStatement ps;
        Bill bill = new Bill();
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                bill.setId(rs.getInt("id"));
                bill.setAccount_id(rs.getInt("account_id"));
                bill.setName(rs.getString("name"));
                bill.setCreated_at(rs.getString("create_at"));
                bill.setUpdated_at(rs.getString("updated_at"));
                bill.setTime(rs.getString("time"));
                bill.setQuantity(rs.getInt("quantity"));
                bill.setPrice(rs.getInt("price"));
                bill.setTotal_price(rs.getInt("total_price"));
                bill.setStatus(rs.getInt("status") == 2 ? "Đã thanh toán" : "Chưa thanh toán");
                bill.setType(rs.getInt("type"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bill;
    }

    public List<Bill> getBillStatistical(int role)
    {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.time AS month, COUNT(DISTINCT a.id) AS count_members, "
                + "AVG(a.age) AS avg_age FROM bills b JOIN accounts a ON b.account_id = a.id "
                + "WHERE a.role = " +role+ " GROUP BY b.time ORDER BY RIGHT(b.time, 4), LEFT(b.time, 2)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Bill bill = new Bill();
                bill.setTime(rs.getString("month"));
                bill.setCountMembers(rs.getInt("count_members"));
                bill.setAvgAge(rs.getDouble("avg_age"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bills;
    }
    public List<Bill> getTeacherBillDataById(int teacherId)
    {
        List<Bill> billList = new ArrayList<>();
        String sql = "SELECT b.id, a.id AS teacher_id, a.name AS teacher_name, b.time AS month_taught, " +
                "COUNT(l.id) AS total_lessons, b.total_price, b.status " +
                "FROM bills b JOIN accounts a ON b.account_id = a.id " +
                "LEFT JOIN classes c ON a.id = c.teacher_id " +
                "LEFT JOIN lessons l ON c.id = l.class_id " +
                "AND b.time = CAST(DATE_FORMAT(STR_TO_DATE(l.title, '%d/%m/%Y'), '%m/%Y') AS CHAR) " +
                "WHERE a.id = " + teacherId + " " +
                "GROUP BY a.id, a.name, b.time, b.total_price, b.status, b.id " +
                "ORDER BY b.time;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAccount_id(rs.getInt("teacher_id"));
                bill.setName(rs.getString("teacher_name"));
                bill.setTime(rs.getString("month_taught"));
                bill.setLessonQty(rs.getInt("total_lessons"));
                bill.setTotal_price(rs.getInt("total_price"));
                bill.setStatus(rs.getInt("status") == 2 ? "Đã thanh toán" : "Chưa thanh toán");
                billList.add(bill);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return billList;
    }
}
