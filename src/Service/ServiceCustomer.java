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

public class ServiceCustomer {
    static ArrayList<Customer> listCustomer = new ArrayList<Customer>();

    private static boolean isInputBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private boolean checkInput() {
        if (listCustomer == null || listCustomer.isEmpty()) {
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
    public Customer addCustomerSQL() {
        System.out.println("Nhập thông tin khách hàng : ");
        Customer customer = inputCustomerInfo();
        new CustomerDAO().insertCustomer(customer);
        return customer;
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
    public static Customer addCustomerExel(){
        System.out.println("Enter room information : ");
        Customer customer = inputCustomerInfo();
        customer.setID(getMaxId() + 1);
        listCustomer.add(customer);
        return customer;
    }
    public void searchCustomersExel() {
        System.out.println("tất cả các khách hàng hiện có trong hệ thống : ");
        if (listCustomer != null) {
            for (Customer customer : listCustomer) {
                System.out.println("\n" + customer.toString());
            }
        }
    }
    public void luuDanhSachKhachHang(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (Customer customer : listCustomer) {
            data.add(new String[]{
                    String.valueOf(customer.getID()),
                    customer.getTen(),
                    customer.getNamSinh(),
                    customer.getCCCD(),
                    String.valueOf(customer.getSoNguoi())
            });
        }
        String[] headers = {"ID Khách Hàng", "Tên Khách Hàng", "Năm Sinh", "Số CCCD", "Số Người"};
        exportToExcel(data, headers,filePath);
        System.out.println("Danh sách khách hàng đã được lưu vào file: " + filePath);
    }
    public void docDanhSachKhachHangTuFileExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên
            listCustomer.clear(); // Xóa dữ liệu cũ trước khi load

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề (i = 1)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Customer customer = new Customer();
                customer.setID(getLongValue(row.getCell(0)));
                customer.setTen(row.getCell(1).getStringCellValue());
                customer.setNamSinh(row.getCell(2).getStringCellValue());
                customer.setCCCD(row.getCell(3).getStringCellValue());
                customer.setSoNguoi(getIntValue(row.getCell(4)));
                listCustomer.add(customer);

            }

            System.out.println("KH Đã import danh sách đặt phòng từ file Excel.");
        } catch (IOException e) {
            System.out.println("KH Lỗi đọc file Excel: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" KH Lỗi định dạng dữ liệu trong file Excel: " + e.getMessage());
        }
    }

    private static Long getMaxId() {
        if (listCustomer == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (Customer customer : listCustomer) {
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
}
