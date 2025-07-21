package constant;

public enum LoaiPhong {
    PHONG_DON("Phòng đơn"),
    PHONG_DOI("Phòng đôi"),
    PHONG_VIP("Phòng vip"),
    PHONG_TONG_THONG("Phòng tổng thống");

    private String description;

    LoaiPhong(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
