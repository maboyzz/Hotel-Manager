package Validator;

public class CustomerValidator {
    public static boolean isValidFullName(String fullName) {

        if (fullName.length() > 100) {
            System.out.println("Error: tên quá dài");
            return false;
        }
        return true;
    }
}
