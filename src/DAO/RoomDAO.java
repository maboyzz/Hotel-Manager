package DAO;

import Model.Room;
import constant.LoaiPhong;
import constant.TinhTrang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public void insertRoom(Room room) {
        String sql = "INSERT INTO phong (ten_phong, kich_thuoc, tinh_nang, trang_thai, loai_phong, gia_phong) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getTenPhong());
            stmt.setString(2, room.getKichThuoc());
            stmt.setString(3, room.getTinhNang());
            stmt.setString(4, room.getTrangThai().name());
            stmt.setString(5, room.getLoaiPhong().name());
            stmt.setLong(6, room.getGiaPhong());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> findAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM phong";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room room = new Room();
                room.setID(rs.getLong("ma_phong"));
                room.setTenPhong(rs.getString("ten_phong"));
                room.setKichThuoc(rs.getString("kich_thuoc"));
                room.setTinhNang(rs.getString("tinh_nang"));
                room.setTrangThai(TinhTrang.valueOf(rs.getString("trang_thai")));
                room.setLoaiPhong(LoaiPhong.valueOf(rs.getString("loai_phong")));
                room.setGiaPhong(rs.getLong("gia_phong"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> findAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM phong WHERE trang_thai = 'PHONG_TRONG'";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room room = new Room();
                room.setID(rs.getLong("ma_phong"));
                room.setTenPhong(rs.getString("ten_phong"));
                room.setKichThuoc(rs.getString("kich_thuoc"));
                room.setTinhNang(rs.getString("tinh_nang"));
                room.setTrangThai(TinhTrang.valueOf(rs.getString("trang_thai")));
                room.setLoaiPhong(LoaiPhong.valueOf(rs.getString("loai_phong")));
                room.setGiaPhong(rs.getLong("gia_phong"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room findRoomById(Long id) {
        String sql = "SELECT * FROM phong WHERE ma_phong = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setID(rs.getLong("ma_phong"));
                    room.setTenPhong(rs.getString("ten_phong"));
                    room.setKichThuoc(rs.getString("kich_thuoc"));
                    room.setTinhNang(rs.getString("tinh_nang"));
                    room.setTrangThai(TinhTrang.valueOf(rs.getString("trang_thai")));
                    room.setLoaiPhong(LoaiPhong.valueOf(rs.getString("loai_phong")));
                    room.setGiaPhong(rs.getLong("gia_phong"));
                    return room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRoomStatus(Long phongId, TinhTrang trangThaiMoi) {
        String sql = "UPDATE phong SET trang_thai = ? WHERE ma_phong = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThaiMoi.name());
            stmt.setLong(2, phongId);

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
    public void updateRoom(Room room) {
        String sql = "UPDATE phong SET ten_phong = ?, loai_phong = ?, gia_phong = ?, trang_thai = ?, kich_thuoc = ?, tinh_nang = ? WHERE ma_phong = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getTenPhong());
            stmt.setString(2, room.getLoaiPhong().name());
            stmt.setLong(3, room.getGiaPhong());
            stmt.setString(4, room.getTrangThai().name());
            stmt.setString(5, room.getKichThuoc());
            stmt.setString(6, room.getTinhNang());


            stmt.setLong(7, room.getID());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật phòng thành công!");
            } else {
                System.out.println("Không tìm thấy phòng để cập nhật!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteRoom(Long phongId) {
        String sql = "DELETE FROM phong WHERE ma_phong = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, phongId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Xoa phòng thành công!");
            } else {
                System.out.println("Không tìm thấy phòng để xóa !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}