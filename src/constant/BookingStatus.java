package constant;

public enum BookingStatus {
    DA_THANH_TOAN("Đã thanh toán"),
    CHUA_THANH_TOAN("Chưa thanh toán");


    private String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
