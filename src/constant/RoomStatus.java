package constant;

public enum RoomStatus {
    PHONG_TRONG("Phòng trống"),
    CHO_XAC_NHAN("Chờ xác nhận"),
    DA_THUE("Đã thuê"),
    CHO_DON_DEP("Chờ dọn dẹp"),;


    private String description;

    RoomStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
