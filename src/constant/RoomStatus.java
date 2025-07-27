package constant;

public enum RoomStatus {
    AVAILABLE("Phòng trống"),
    PENDING_CONFIRMATION("Chờ xác nhận"),
    OCCUPIED("Đã thuê"),
    PENDING_CLEANING("Chờ dọn dẹp");

    private final String description;

    RoomStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}