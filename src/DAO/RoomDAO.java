package DAO;

import Model.Room;
import constant.RoomType;
import constant.RoomStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public void insertRoom(Room room) {
        String sql = "INSERT INTO rooms (room_name, size, features, status, room_type, price) VALUES (?, ?, ?, ?, ?, ?)";
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
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("room_id"));
                room.setRoomName(rs.getString("room_name"));
                room.setRoomSize(rs.getString("size"));
                room.setFeatures(rs.getString("features"));
                room.setStatus(RoomStatus.valueOf(rs.getString("status")));
                room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
                room.setPrice(rs.getLong("price"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> findAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = 'AVAILABLE'";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("room_id"));
                room.setRoomName(rs.getString("room_name"));
                room.setRoomSize(rs.getString("size"));
                room.setFeatures(rs.getString("features"));
                room.setStatus(RoomStatus.valueOf(rs.getString("status")));
                room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
                room.setPrice(rs.getLong("price"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room findRoomById(Long id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setId(rs.getLong("room_id"));
                    room.setRoomName(rs.getString("room_name"));
                    room.setRoomSize(rs.getString("size"));
                    room.setFeatures(rs.getString("features"));
                    room.setStatus(RoomStatus.valueOf(rs.getString("status")));
                    room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
                    room.setPrice(rs.getLong("price"));
                    return room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRoomStatus(Long roomId, RoomStatus newStatus) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus.name());
            stmt.setLong(2, roomId);

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
        String sql = "UPDATE rooms SET room_name = ?, room_type = ?, price = ?, status = ?, size = ?, features = ? WHERE room_id = ?";

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

    public void deleteRoom(Long roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection conn = DAOConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, roomId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Xóa phòng thành công!");
            } else {
                System.out.println("Không tìm thấy phòng để xóa!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}