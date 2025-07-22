package Validator;

import java.util.regex.Pattern;

public class CustomerValidator {

    private static int namHienTai = java.time.Year.now().getValue();

    public static boolean isValidFullName(String fullName) {

        if (fullName.length() > 100) {
            System.out.println("Error: tên quá dài");
            return false;
        }
        return true;
    }

    public static boolean isValidNamSinh(String namSinh) {
        Pattern pattern = Pattern.compile("\\d+");
        if (!pattern.matcher(namSinh).matches()) {
            System.out.println("Error: năm sinh phải là 1 số");
            return false;
        }
        if (namSinh.length() != 4) {
            System.out.println("năm sinh chỉ 4 số");
            return false;
        }
        int tuoi = namHienTai - Integer.parseInt(namSinh);
        if (tuoi < 18 || tuoi > 100) {
            System.out.println("Error: bạn phải từ 18-100 tuổi");
            return false;
        }
        return true;
    }

    public static boolean isValidCCCD(String cccd) {
        Pattern pattern = Pattern.compile("^\\d{12}$");

        if (!pattern.matcher(cccd).matches()) {
            System.out.println("Error: CCCD phải gồm đúng 12 chữ số");
            return false;
        }

        return true;
    }

    public static boolean isValidSoNguoi(String soNguoi) {
        Pattern pattern = Pattern.compile("\\d+");

        if (!pattern.matcher(soNguoi).matches()) {
            System.out.println("Error: bạn phải nhập số.");
            return false;
        }

        int so = Integer.parseInt(soNguoi);
        if (so <= 0) {
            System.out.println("Error: số người phải lớn hơn 0.");
            return false;
        }
        return true;
    }
}
