package DAO;
import Model.DatPhong;

import java.sql.*;

public class DatPhongDAO {
    public void datPhong(DatPhong dp) {
        String sql = "INSERT INTO dat_phong ( khach_hang_id, phong_id, ngay_nhan, ngay_tra, ghi_chu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, dp.getMaKhachHang());
            stmt.setLong(2, dp.getMaPhong());
            stmt.setTimestamp(3, Timestamp.valueOf(dp.getThoiGianDat()));
            stmt.setTimestamp(4, Timestamp.valueOf(dp.getThoiGianDat()));
            stmt.setString(5, dp.getGhiChu());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
