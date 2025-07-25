package DAO;

import Model.Room;
import constant.RoomType;
import constant.RoomStatus;

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
            stmt.setString(1, room.getRoomName());
            stmt.setString(2, room.getRoomSize());
            stmt.setString(3, room.getFeatures());
            stmt.setString(4, room.getStatus().name());
            stmt.setString(5, room.getRoomType().name());
            stmt.setLong(6, room.getPrice());
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
                room.setId(rs.getLong("ma_phong"));
                room.setRoomName(rs.getString("ten_phong"));
                room.setRoomSize(rs.getString("kich_thuoc"));
                room.setFeatures(rs.getString("tinh_nang"));
                room.setStatus(RoomStatus.valueOf(rs.getString("trang_thai")));
                room.setRoomType(RoomType.valueOf(rs.getString("loai_phong")));
                room.setPrice(rs.getLong("gia_phong"));
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
                room.setId(rs.getLong("ma_phong"));
                room.setRoomName(rs.getString("ten_phong"));
                room.setRoomSize(rs.getString("kich_thuoc"));
                room.setFeatures(rs.getString("tinh_nang"));
                room.setStatus(RoomStatus.valueOf(rs.getString("trang_thai")));
                room.setRoomType(RoomType.valueOf(rs.getString("loai_phong")));
                room.setPrice(rs.getLong("gia_phong"));
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
                    room.setId(rs.getLong("ma_phong"));
                    room.setRoomName(rs.getString("ten_phong"));
                    room.setRoomSize(rs.getString("kich_thuoc"));
                    room.setFeatures(rs.getString("tinh_nang"));
                    room.setStatus(RoomStatus.valueOf(rs.getString("trang_thai")));
                    room.setRoomType(RoomType.valueOf(rs.getString("loai_phong")));
                    room.setPrice(rs.getLong("gia_phong"));
                    return room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRoomStatus(Long phongId, RoomStatus trangThaiMoi) {
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

            stmt.setString(1, room.getRoomName());
            stmt.setString(2, room.getRoomType().name());
            stmt.setLong(3, room.getPrice());
            stmt.setString(4, room.getStatus().name());
            stmt.setString(5, room.getRoomSize());
            stmt.setString(6, room.getFeatures());


            stmt.setLong(7, room.getId());

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