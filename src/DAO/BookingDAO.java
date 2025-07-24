package DAO;
import Model.DatPhong;
import constant.TrangThai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public void insertBooking(DatPhong dp) {
        String sql = "INSERT INTO dat_phong ( khach_hang_id, phong_id, ngay_nhan, ngay_tra, ghi_chu,trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, dp.getMaKhachHang());
            stmt.setLong(2, dp.getMaPhong());
            stmt.setTimestamp(3, Timestamp.valueOf(dp.getThoiGianDat()));
            stmt.setTimestamp(4, Timestamp.valueOf(dp.getThoiGianTra()));
            stmt.setString(5, dp.getGhiChu());
            stmt.setString(6, TrangThai.CHUA_THANH_TOAN.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public DatPhong findBookingById(Long id) {
        String sql = "SELECT * FROM dat_phong WHERE id = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DatPhong dp = new DatPhong();
                    dp.setId(rs.getLong("id"));
                    dp.setMaKhachHang(rs.getLong("khach_hang_id"));
                    dp.setMaPhong(rs.getLong("phong_id"));
                    dp.setThoiGianDat(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                    dp.setThoiGianTra(rs.getTimestamp("ngay_tra").toLocalDateTime());
                    dp.setGhiChu(rs.getString("ghi_chu"));
                    return dp;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<DatPhong> findAllBookings() {
        List<DatPhong> hoadons = new ArrayList<>();

        String sql = "SELECT * FROM dat_phong";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setId(rs.getLong("id"));
                dp.setMaKhachHang(rs.getLong("khach_hang_id"));
                dp.setMaPhong(rs.getLong("phong_id"));
                dp.setThoiGianDat(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                dp.setThoiGianTra(rs.getTimestamp("ngay_tra").toLocalDateTime());
                dp.setGhiChu(rs.getString("ghi_chu"));
                dp.setTrangThai(TrangThai.valueOf(rs.getString("trang_thai")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public List<DatPhong> findUnpaidBookings() {
        List<DatPhong> hoadons = new ArrayList<>();

        String sql = "SELECT * FROM dat_phong where trang_thai = 'CHUA_THANH_TOAN'";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setId(rs.getLong("id"));
                dp.setMaKhachHang(rs.getLong("khach_hang_id"));
                dp.setMaPhong(rs.getLong("phong_id"));
                dp.setThoiGianDat(rs.getTimestamp("ngay_nhan").toLocalDateTime());
                dp.setThoiGianTra(rs.getTimestamp("ngay_tra").toLocalDateTime());
                dp.setGhiChu(rs.getString("ghi_chu"));
                dp.setTrangThai(TrangThai.valueOf(rs.getString("trang_thai")));
                hoadons.add(dp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoadons;
    }
    public void updateBookingStatus(Long dpId, TrangThai trangThaiMoi) {
        String sql = "UPDATE dat_phong SET trang_thai = ? WHERE id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThaiMoi.name());
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
