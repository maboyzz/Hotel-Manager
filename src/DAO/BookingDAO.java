package DAO;
import Model.Booking;
import constant.BookingStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public void insertBooking(Booking dp) {
        String sql = "INSERT INTO dat_phong ( khach_hang_id, phong_id, ngay_nhan, ngay_tra, ghi_chu,trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, dp.getCustomerId());
            stmt.setLong(2, dp.getRoomId());
            stmt.setTimestamp(3, Timestamp.valueOf(dp.getCheckInTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(dp.getCheckOutTime()));
            stmt.setString(5, dp.getNote());
            stmt.setString(6, BookingStatus.CHUA_THANH_TOAN.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Booking findBookingById(Long id) {
        String sql = "SELECT * FROM dat_phong WHERE id = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Booking dp = new Booking();
                    dp.setId(rs.getLong("id"));
                    dp.setCustomerId(rs.getLong("khach_hang_id"));
                    dp.setRoomId(rs.getLong("phong_id"));
                    dp.setCheckInTime(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                    dp.setCheckOutTime(rs.getTimestamp("ngay_tra").toLocalDateTime());
                    dp.setNote(rs.getString("ghi_chu"));
                    return dp;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<Booking> findAllBookings() {
        List<Booking> hoadons = new ArrayList<>();

        String sql = "SELECT * FROM dat_phong";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking dp = new Booking();
                dp.setId(rs.getLong("id"));
                dp.setCustomerId(rs.getLong("khach_hang_id"));
                dp.setRoomId(rs.getLong("phong_id"));
                dp.setCheckInTime(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                dp.setCheckOutTime(rs.getTimestamp("ngay_tra").toLocalDateTime());
                dp.setNote(rs.getString("ghi_chu"));
                dp.setStatus(BookingStatus.valueOf(rs.getString("trang_thai")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public List<Booking> findUnpaidBookings() {
        List<Booking> hoadons = new ArrayList<>();

        String sql = "SELECT * FROM dat_phong where trang_thai = 'CHUA_THANH_TOAN'";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking dp = new Booking();
                dp.setId(rs.getLong("id"));
                dp.setCustomerId(rs.getLong("khach_hang_id"));
                dp.setRoomId(rs.getLong("phong_id"));
                dp.setCheckInTime(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                dp.setCheckOutTime(rs.getTimestamp("ngay_tra").toLocalDateTime());
                dp.setNote(rs.getString("ghi_chu"));
                dp.setStatus(BookingStatus.valueOf(rs.getString("trang_thai")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public void updateBookingStatus(Long dpId, BookingStatus bookingStatusMoi) {
        String sql = "UPDATE dat_phong SET trang_thai = ? WHERE id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingStatusMoi.name());
            stmt.setLong(2, dpId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật trạng thái phòng thành công!");
            } else {
                System.out.println("Không tìm thấy phòng để cập nhật!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
