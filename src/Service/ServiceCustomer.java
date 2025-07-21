package Service;

import Model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static Validator.CustomerValidator.isValidFullName;

public class ServiceCustomer {
    ArrayList<ServiceCustomer> listCustomer = new ArrayList<ServiceCustomer>();

    private boolean isInputBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
    private boolean checkInput() {
        if (listCustomer == null || listCustomer.isEmpty()) {
            System.out.println("Error: No matching data.");
            return false;
        }
        return true;
    }
    private Customer customerInfo() {
        Customer customer = new Customer();
        Scanner sc = new Scanner(System.in);
        String ten;
        String namSinh;
        String cccd;
        int soNguoi;

        do {
            System.out.println("nhập tên khách hàng: ");
            ten = sc.nextLine();
            if (!isInputBlank(ten) && isValidFullName(ten)) {
                customer.setTen(ten);
                break;
            }
        } while (true);
        do {
            System.out.println("nhập năm sinh: ");
            ten = sc.nextLine();
            if (!isInputBlank(ten) && isValidFullName(ten)) {
                customer.setTen(ten);
                break;
            }
        } while (true);
        do {
            System.out.println("Enter customer phone: ");
            phone = sc.nextLine();
            if (!isInputBlank(phone)) {
                customer.setPhone(phone);
                break;
            }
        } while (true);

        do {
            System.out.println("Enter customer email: ");
            email = sc.nextLine();
            if (!isInputBlank(email)) {
                customer.setEmail(email);
                break;
            }
        } while (true);

        return customer;
    }

}
