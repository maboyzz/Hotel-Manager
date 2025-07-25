package Model;

import constant.RoomType;
import constant.RoomStatus;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Long id;
    private String roomName;
    private RoomType roomType;
    private String roomSize;
    private RoomStatus status;
    private String features;
    private long price;

    public Room(Long id, String roomName, String roomSize, RoomType roomType, RoomStatus status, String features, long price) {
        this.id = id;
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.roomType = roomType;
        this.status = status;
        this.features = features;
        this.price = price;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }


    @Override
    public String toString() {
        int width = 60; // độ dài tối đa cho mỗi dòng của "Tính năng"
        List<String> lines = splitText(features, width);

        StringBuilder sb = new StringBuilder();

        // Dòng đầu tiên: in đầy đủ
        sb.append(String.format("| %-2d | %-6s | %-18s | %-10s | %-13s | %-60s | %-9.2f |",
                id, roomName, roomType, roomSize, status, lines.get(0), (double) price));

        // Các dòng tiếp theo: chỉ in phần tính năng
        for (int i = 1; i < lines.size(); i++) {
            sb.append(String.format("\n| %-2s | %-6s | %-18s | %-10s | %-13s | %-60s | %-9s |",
                    "", "", "", "", "", lines.get(i), ""));
        }

        return sb.toString();
    }
    private List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        while (text.length() > maxLength) {
            int splitPos = text.lastIndexOf(", ", maxLength);
            if (splitPos == -1) splitPos = maxLength;
            result.add(text.substring(0, splitPos).trim());
            text = text.substring(splitPos).replaceFirst("^,\\s*", "").trim();
        }
        result.add(text);
        return result;
    }
}
