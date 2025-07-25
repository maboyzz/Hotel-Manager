package Service;

import DAO.CustomerDAO;
import Model.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static DAO.ExportToExcel.exportToExcel;
import static Validator.CustomerValidator.*;

public class CustomerService {
    static ArrayList<Customer> customerList = new ArrayList<Customer>();

    private static boolean isInputBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private boolean checkInput() {
        if (customerList == null || customerList.isEmpty()) {
            System.out.println("Error: No matching data.");
            return false;
        }
        return true;
    }

    private static Customer inputCustomerInfo() {
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
                customer.setName(ten);
                break;
            }
        } while (true);
        do {
            System.out.println("nhập năm sinh: ");
            namSinh = sc.nextLine();
            if (!isInputBlank(namSinh) && isValidNamSinh(namSinh)) {
                customer.setBirthYear(namSinh);
                break;
            }
        } while (true);
        do {
            System.out.println("Nhập căn cước công dân ");
            cccd = sc.nextLine();
            if (!isInputBlank(cccd) && isValidCCCD(cccd)) {
                customer.setCitizenId(cccd);
                break;
            }
        } while (true);
        tmp:
        do {
            System.out.println("nhập số người : ");
            soNguoi = sc.nextLine();
            if (!isInputBlank(soNguoi) && isValidSoNguoi(soNguoi)) {
                customer.setNumberOfPeople(Integer.parseInt(soNguoi));
                break;
            }
        } while (true);

        return customer;
    }
    public static Customer inputCustomerInfoCCCD() {
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
                customer.setName(ten);
                break;
            }
        } while (true);
        do {
            System.out.println("nhập năm sinh: ");
            namSinh = sc.nextLine();
            if (!isInputBlank(namSinh) && isValidNamSinh(namSinh)) {
                customer.setBirthYear(namSinh);
                break;
            }
        } while (true);
        tmp:
        do {
            System.out.println("nhập số người : ");
            soNguoi = sc.nextLine();
            if (!isInputBlank(soNguoi) && isValidSoNguoi(soNguoi)) {
                customer.setNumberOfPeople(Integer.parseInt(soNguoi));
                break;
            }
        } while (true);

        return customer;
    }

    public Customer addCustomerSQL() {
        System.out.println("Nhập thông tin khách hàng : ");
        Customer customer = inputCustomerInfo();
        new CustomerDAO().insertCustomer(customer);
        return customer;
    }
    public void findAllCustomersSQL() {

        List<Customer> customers = new CustomerDAO().findAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào");
        } else {
            printHeader();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
            System.out.println("+------------+----------------------+------------+-----------------+------------+");
        }
    }
    public void updateCustomerByIdSQL() {
        List<Customer> customers = new CustomerDAO().findAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ID khách hàng cần cập nhật: ");
        long id = sc.nextLong();
        sc.nextLine(); // Xóa dòng new line còn lại

        Customer customer = new CustomerDAO().findCustomerById(id);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + id);
            return;
        }

        System.out.println("Khách hàng hiện tại:");
        System.out.println(customer);

        Customer updateCustomer = inputCustomerInfo();
        updateCustomer.setID(customer.getID());

        boolean success = new CustomerDAO().updateCustomerByID(updateCustomer);
        if (success) {
            System.out.println("Cập nhật thông tin khách hàng thành công.");
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }
    public void deleteCustomerByIdSQL(){
        List<Customer> customers = new CustomerDAO().findAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập ID khách hàng cần xóa: ");
        long id = sc.nextLong();
        sc.nextLine();

        Customer customer = new CustomerDAO().findCustomerById(id);
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + id);
            return;
        }

        System.out.println("Khách hàng hiện tại:");
        System.out.println(customer);

        boolean success = new CustomerDAO().deleteCustomerById(customer.getID());
        if (success) {
            System.out.println("Xóa khách hàng thành công.");
        } else {
            System.out.println("Xóa thất bại.");
        }

    }
    public static Customer addCustomerExcel(){
        System.out.println("Enter customer information : ");
        Customer customer = inputCustomerInfo();
        customer.setID(getMaxId() + 1);
        customerList.add(customer);
        return customer;
    }
    public static Customer addCustomerCCCDExcel(String cccd){
        System.out.println("Enter custom information : ");
        Customer customer = inputCustomerInfoCCCD();
        customer.setCitizenId(cccd);
        customer.setID(getMaxId() + 1);
        customerList.add(customer);
        return customer;
    }
    public void findAllCustomersExcel() {
        System.out.println("tất cả các khách hàng hiện có trong hệ thống : ");
        printHeader();
        if (customerList != null) {
            for (Customer customer : customerList) {
                System.out.println(customer.toString());
            }
            System.out.println("+------------+----------------------+------------+-----------------+------------+");
        }
    }
    public void deleteCustomerByIdExcel() {
        if (customerList != null && !customerList.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập ID của khách hàng để xóa: ");
            Long id = sc.nextLong();

            Customer found = null;
            for (Customer customer : customerList) {
                if (customer.getID().equals(id)) {
                    found = customer;
                    break;
                }
            }

            if (found != null) {
                customerList.remove(found);
                System.out.println("Xóa khách hàng thành công.");
            } else {
                System.out.println("Xóa thất bại, không tìm thấy khách hàng trong dữ liệu.");
            }
        } else {
            System.out.println("Danh sách khách hàng rỗng.");
        }
    }
    public void updateCustomerByIdExcel() {
        if (customerList != null && !customerList.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập ID của khách hàng để cập nhật: ");
            Long id = sc.nextLong();
            sc.nextLine();

            Customer found = null;
            for (Customer customer : customerList) {
                if (customer.getID().equals(id)) {
                    found = customer;
                    break;
                }
            }

            if (found != null) {
                Customer updatedCustomer = inputCustomerInfo();
                updatedCustomer.setID(id);

                int index = customerList.indexOf(found);
                customerList.set(index, updatedCustomer);

                System.out.println("Cập nhật khách hàng thành công.");
            } else {
                System.out.println("Không tìm thấy khách hàng với ID đã nhập.");
            }
        } else {
            System.out.println("Danh sách khách hàng rỗng.");
        }
    }
    public void saveCustomerListToExcel(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (Customer customer : customerList) {
            data.add(new String[]{
                    String.valueOf(customer.getID()),
                    customer.getName(),
                    customer.getBirthYear(),
                    customer.getCitizenId(),
                    String.valueOf(customer.getNumberOfPeople())
            });
        }
        String[] headers = {"ID Khách Hàng", "Tên Khách Hàng", "Năm Sinh", "Số CCCD", "Số Người"};
        exportToExcel(data, headers,filePath);
        System.out.println("Danh sách khách hàng đã được lưu vào file: " + filePath);
    }
    public void loadCustomerListFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên
            customerList.clear(); // Xóa dữ liệu cũ trước khi load

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề (i = 1)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Customer customer = new Customer();
                customer.setID(getLongValue(row.getCell(0)));
                customer.setName(row.getCell(1).getStringCellValue());
                customer.setBirthYear(row.getCell(2).getStringCellValue());
                customer.setCitizenId(row.getCell(3).getStringCellValue());
                customer.setNumberOfPeople(getIntValue(row.getCell(4)));
                customerList.add(customer);

            }

            System.out.println("KH Đã import danh sách đặt phòng từ file Excel.");
        } catch (IOException e) {
            System.out.println("KH Lỗi đọc file Excel: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" KH Lỗi định dạng dữ liệu trong file Excel: " + e.getMessage());
        }
    }

    private static Long getMaxId() {
        if (customerList == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (Customer customer : customerList) {
            if (customer != null && customer.getID() > maxId) {
                maxId = customer.getID();
            }
        }
        return maxId;
    }
    private Long getLongValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Long.parseLong(cell.getStringCellValue());
        }
        return null;
    }
    private Integer getIntValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Integer.parseInt(cell.getStringCellValue());
        }
        return null;
    }
    public static void printHeader() {
        System.out.println("+------------+----------------------+------------+-----------------+------------+");
        System.out.printf("| %-10s | %-20s | %-10s | %-15s | %-10s |\n",
                "ID", "Họ tên", "Năm sinh", "CCCD", "Số người");
        System.out.println("+------------+----------------------+------------+-----------------+------------+");
    }
}
