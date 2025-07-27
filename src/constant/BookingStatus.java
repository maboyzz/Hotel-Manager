package constant;

public enum BookingStatus {

        UNPAID("Chưa thanh toán"),
        PAID("Đã thanh toán");

        private final String description;


    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
