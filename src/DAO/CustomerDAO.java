package DAO;

import Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CustomerDAO {
    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO khach_hang (ten, nam_sinh, cccd, so_nguoi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getTen());
            stmt.setString(2, customer.getNamSinh());
            stmt.setString(3, customer.getCCCD());
            stmt.setInt(4, customer.getSoNguoi());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
