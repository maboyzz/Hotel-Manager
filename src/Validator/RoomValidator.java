package Validator;

import java.util.regex.Pattern;

public class RoomValidator {
    public static boolean isValidFullName(String fullName) {

        if (fullName.length() > 30) {
            System.out.println("Error: Full name exceeds maximum length");
            return false;
        }
        return true;
    }
    public static boolean isValidTinhNang(String tinhNang) {

        if (tinhNang.length() > 300) {
            System.out.println("Error: Tinh nang exceeds maximum length");
            return false;
        }
        return true;
    }
    public static boolean isValidIDPhong(String phong) {
        Pattern pattern = Pattern.compile("\\d+");
        if (!pattern.matcher(phong).matches()) {
            System.out.println("Error: Nhập sai id phòng phải là số !!!");
            return false;
        }
        return true;
    }
}
