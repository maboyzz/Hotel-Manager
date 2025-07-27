package DAO;
import Model.Booking;
import constant.BookingStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public void insertBooking(Booking dp) {
        String sql = "INSERT INTO booking (customer_id, room_id, check_in, check_out, note, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, dp.getCustomerId());
            stmt.setLong(2, dp.getRoomId());
            stmt.setTimestamp(3, Timestamp.valueOf(dp.getCheckInTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(dp.getCheckOutTime()));
            stmt.setString(5, dp.getNote());
            stmt.setString(6, BookingStatus.UNPAID.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Booking findBookingById(Long id) {
        String sql = "SELECT * FROM booking WHERE booking_id = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Booking dp = new Booking();
                    dp.setCustomerId(rs.getLong("customer_id"));
                    dp.setRoomId(rs.getLong("room_id"));
                    dp.setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime());
                    dp.setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime());
                    dp.setNote(rs.getString("note"));
                    dp.setStatus(BookingStatus.valueOf(rs.getString("status")));
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

        String sql = "SELECT * FROM bookings";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking dp = new Booking();
                dp.setCustomerId(rs.getLong("customer_id"));
                dp.setRoomId(rs.getLong("room_id"));
                dp.setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime());
                dp.setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime());
                dp.setNote(rs.getString("note"));
                dp.setStatus(BookingStatus.valueOf(rs.getString("status")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public List<Booking> findUnpaidBookings() {
        List<Booking> hoadons = new ArrayList<>();

        String sql = "SELECT * FROM bookings where status = 'UNPAID'";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking dp = new Booking();
                dp.setCustomerId(rs.getLong("customer_id"));
                dp.setRoomId(rs.getLong("room_id"));
                dp.setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime());
                dp.setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime());
                dp.setNote(rs.getString("note"));
                dp.setStatus(BookingStatus.valueOf(rs.getString("status")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public void updateBookingStatus(Long dpId, BookingStatus bookingStatusMoi) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";

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
