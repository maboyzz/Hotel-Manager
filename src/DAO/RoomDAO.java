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

    public List<Room> TimTatCaPhong() {
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
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> TimPhongTrong() {
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
                room.setGiaPhong(Long.valueOf(rs.getLong("gia_phong")));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

}