package Service;

import DAO.CustomerDAO;
import DAO.RoomDAO;
import Model.Customer;
import Model.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static Validator.CustomerValidator.*;

public class ServiceCustomer {
    ArrayList<Customer> listCustomer = new ArrayList<Customer>();

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

    private Customer inputCustomerInfo() {
        Customer customer = new Customer();
        Scanner sc = new Scanner(System.in);
        String ten;
        String namSinh;
        String cccd;
        String soNguoi;

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
            namSinh = sc.nextLine();
            if (!isInputBlank(namSinh) && isValidNamSinh(namSinh)) {
                customer.setNamSinh(namSinh);
                break;
            }
        } while (true);
        do {
            System.out.println("Nhập căn cước công dân ");
            cccd = sc.nextLine();
            if (!isInputBlank(cccd) && isValidCCCD(cccd)) {
                customer.setCCCD(cccd);
                break;
            }
        } while (true);
        tmp:
        do {
            System.out.println("nhập số người : ");
            soNguoi = sc.nextLine();
            if (!isInputBlank(soNguoi) && isValidSoNguoi(soNguoi)) {
                customer.setSoNguoi(Integer.parseInt(soNguoi));
                break;
            }
        } while (true);

        return customer;
    }
    public void addCustomerSQL() {
        System.out.println("Nhập thông tin khách hàng : ");
        Customer customer = inputCustomerInfo();
        new CustomerDAO().insertCustomer(customer);
        System.out.println("Đã thêm khách hàng vào database :  " + customer);
    }
    public void searchCustomerSQL() {

        List<Customer> customers = new CustomerDAO().TimTatCaKhachHang();
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

}
