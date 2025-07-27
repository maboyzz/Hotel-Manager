package DAO;

import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public Long insertCustomer(Customer customer) {
        String checkSql = "SELECT customer_id FROM customers WHERE citizen_id = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, customer.getCitizenId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Khách hàng đã tồn tại, sử dụng lại ID: " + rs.getLong("customer_id"));
                return rs.getLong("customer_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        String insertSql = "INSERT INTO customers (full_name, birth_year, citizen_id, people_count) VALUES (?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            insertStmt.setString(1, customer.getName());
            insertStmt.setString(2, customer.getBirthYear());
            insertStmt.setString(3, customer.getCitizenId());
            insertStmt.setInt(4, customer.getNumberOfPeople());

            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Insert failed, no rows affected.");

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

    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setID(rs.getLong("customer_id"));
                customer.setName(rs.getString("full_name"));
                customer.setBirthYear(rs.getString("birth_year"));
                customer.setCitizenId(rs.getString("citizen_id"));
                customer.setNumberOfPeople(rs.getInt("people_count"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public Customer findCustomerById(Long id) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        Customer customer = null;

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setID(rs.getLong("customer_id"));
                customer.setName(rs.getString("full_name"));
                customer.setBirthYear(rs.getString("birth_year"));
                customer.setCitizenId(rs.getString("citizen_id"));
                customer.setNumberOfPeople(rs.getInt("people_count"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public boolean updateCustomerByID(Customer customer) {
        String sql = "UPDATE customers SET full_name = ?, birth_year = ?, citizen_id = ?, people_count = ? WHERE customer_id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getBirthYear());
            stmt.setString(3, customer.getCitizenId());
            stmt.setInt(4, customer.getNumberOfPeople());
            stmt.setLong(5, customer.getID());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Cập nhật khách hàng thành công!");
                return true;
            } else {
                System.out.println("❌ Không tìm thấy khách hàng để cập nhật!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật khách hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomerById(Long id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Xóa khách hàng thành công!");
                return true;
            } else {
                System.out.println("❌ Không tìm thấy khách hàng để xóa!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa khách hàng: " + e.getMessage());
            return false;
        }
    }
}