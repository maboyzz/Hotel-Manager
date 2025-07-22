package Validator;


import java.time.LocalDateTime;

public class DatPhongValidator {
    public static boolean isValidNgayTra(LocalDateTime ngayNhan, LocalDateTime ngayTra) {
        if (ngayTra == null || ngayNhan == null) {
            System.out.println("Ngày nhận hoặc ngày trả không được để trống.");
            return false;
        }

        if (!ngayTra.isAfter(ngayNhan)) {
            System.out.println("Ngày trả phải sau ngày nhận (hiện tại).");
            return false;
        }

        return true;
    }

    public static boolean isValidPhongId(Long phongId) {
        if (phongId == null || phongId <= 0) {
            System.out.println("Mã phòng không hợp lệ.");
            return false;
        }
        return true;
    }
}