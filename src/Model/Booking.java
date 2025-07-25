package Model;

import constant.BookingStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private Long id;
    private Long customerId;
    private Long roomId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String note;
    private BookingStatus status;

    public Booking(Long id, Long customerId, Long roomId, LocalDateTime checkInTime, LocalDateTime checkOutTime, String note, BookingStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.note = note;
        this.status = status;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        int width = 30; // chiều dài tối đa mỗi dòng ghi chú
        List<String> ghiChuLines = splitText(note, width);

        StringBuilder sb = new StringBuilder();

        // Dòng đầu tiên in đủ
        sb.append(String.format("| %-4s | %-10s | %-8s | %-19s | %-19s | %-30s | %-10s |",
                id, customerId, roomId, checkInTime, checkOutTime, ghiChuLines.get(0), status));

        // Các dòng tiếp theo in tiếp phần ghi chú
        for (int i = 1; i < ghiChuLines.size(); i++) {
            sb.append(String.format("\n| %-4s | %-10s | %-8s | %-19s | %-19s | %-30s | %-10s |",
                    "", "", "", "", "", ghiChuLines.get(i), ""));
        }

        return sb.toString();
    }
    private List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        while (text.length() > maxLength) {
            int splitPos = text.lastIndexOf(" ", maxLength);
            if (splitPos == -1) splitPos = maxLength;
            result.add(text.substring(0, splitPos).trim());
            text = text.substring(splitPos).trim();
        }
        result.add(text);
        return result;
    }
}