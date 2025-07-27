package constant;

public enum RoomType {
    SINGLE("Phòng đơn"),
    DOUBLE("Phòng đôi"),
    VIP("Phòng vip"),
    PRESIDENTIAL("Phòng tổng thống");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
