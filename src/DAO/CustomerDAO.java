package DAO;

import Model.Customer;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Long insertCustomer(Customer customer) {
        String checkSql = "SELECT id FROM khach_hang WHERE cccd = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, customer.getCCCD());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // CCCD đã tồn tại → trả về ID có sẵn
                return rs.getLong("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        String insertSql = "INSERT INTO khach_hang (ten, nam_sinh, cccd, so_nguoi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            insertStmt.setString(1, customer.getTen());
            insertStmt.setString(2, customer.getNamSinh());
            insertStmt.setString(3, customer.getCCCD());
            insertStmt.setInt(4, customer.getSoNguoi());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            // 3. Lấy ra ID mới được tạo từ auto_increment
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Customer> TimTatCaKhachHang() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setID(rs.getLong("id"));
                customer.setTen(rs.getString("ten"));
                customer.setNamSinh(rs.getString("nam_sinh"));
                customer.setCCCD(rs.getString("cccd"));
                customer.setSoNguoi(rs.getInt("so_nguoi"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
    public Customer selectCustomerById(Long id) {
        String sql = "SELECT * FROM khach_hang WHERE id = ?";
        Customer customer = null;
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setID(rs.getLong("id"));
                customer.setTen(rs.getString("ten"));
                customer.setNamSinh(rs.getString("nam_sinh"));
                customer.setCCCD(rs.getString("cccd"));
                customer.setSoNguoi(rs.getInt("so_nguoi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
