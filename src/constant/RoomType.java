package constant;

public enum RoomType {
    PHONG_DON("Phòng đơn"),
    PHONG_DOI("Phòng đôi"),
    PHONG_VIP("Phòng vip"),
    PHONG_TONG_THONG("Phòng tổng thống");

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
