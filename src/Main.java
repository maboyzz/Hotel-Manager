import Model.Room;
import Service.ServiceCustomer;
import Service.ServiceDatPhong;
import Service.ServiceRoom;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceRoom servicePhong = new ServiceRoom();
        ServiceCustomer serviceCustomer = new ServiceCustomer();
        ServiceDatPhong serviceDatPhong = new ServiceDatPhong();

        Scanner sc = new Scanner(System.in);
        String filePath = "output.xlsx";
        boolean isExit = false;

        // Tự động lưu file Excel khi thoát chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            servicePhong.luuDanhSachPhong(filePath);
            System.out.println("Danh sách phòng đã được lưu vào " + filePath);
        }));

        while (!isExit) {
            displayMainMenu();
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSQLMenu(serviceDatPhong, serviceCustomer, servicePhong, sc);
                    break;
                case "2":
                    handleExcelMenu(serviceCustomer, servicePhong, sc);
                    break;
                case "3":
                    isExit = true;
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    break;
            }
        }
        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Hotel Manager ===");
        System.out.println("1. Quản lý Phòng SQL");
        System.out.println("2. Quản lý Phòng Excel");
        System.out.println("3. Thoát");
    }

    private static void handleSQLMenu(ServiceDatPhong serviceDatPhong, ServiceCustomer serviceCustomer, ServiceRoom servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Quản lý SQL ===");
            System.out.println("1. Quản lý Phòng");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Đặt phòng / Thanh toán");
            System.out.println("4. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    handleRoomMenu(servicePhong, sc);
                    break;
                case "2":
                    handleCustomerMenu(serviceCustomer, sc);
                    break;
                case "3":
                    handleBookingMenu(serviceDatPhong, sc);
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    break;
            }
        }
    }

    private static void handleRoomMenu(ServiceRoom servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Quản lý Phòng ---");
            System.out.println("1. Tìm kiếm phòng trống");
            System.out.println("2. Hiển thị tất cả phòng");
            System.out.println("3. Thêm phòng mới");
            System.out.println("4. Cập nhật phòng");
            System.out.println("5. Xóa phòng");
            System.out.println("6. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    servicePhong.timPhongtrongSQL();
                    break;
                case "2":
                    servicePhong.timToanBoPhongSQL();
                    break;
                case "3":
                    servicePhong.addRoomsSQL();
                    break;
                case "4":
                    servicePhong.timToanBoPhongSQL();
                    servicePhong.capNhatPhongSQL();
                    break;
                case "5":
                    servicePhong.timToanBoPhongSQL();
                    servicePhong.xoaPhongSQL();
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleCustomerMenu(ServiceCustomer serviceCustomer, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Quản lý Khách hàng ---");
            System.out.println("1. Tìm kiếm khách hàng");
            System.out.println("2. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    serviceCustomer.searchCustomerSQL();
                    break;
                case "2":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleBookingMenu(ServiceDatPhong serviceDatPhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Quản lý Đặt phòng & Thanh toán ---");
            System.out.println("1. Tạo đặt phòng");
            System.out.println("2. Xem danh sách hóa đơn");
            System.out.println("3. Trả phòng và thanh toán");
            System.out.println("4. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    serviceDatPhong.taoKhachVaDatPhong();
                    break;
                case "2":
                    serviceDatPhong.hienThiTatCaHoaDon();
                    break;
                case "3":
                    serviceDatPhong.hienThiTatCaHoaDonChuaThanhToan();
                    serviceDatPhong.traPhongVaThanhToan();

                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleExcelMenu(ServiceCustomer serviceCustomer, ServiceRoom servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Quản lý Excel ===");
            System.out.println("1. Tìm kiếm phòng trống");
            System.out.println("2. Thanh toán phòng");
            System.out.println("3. Lựa chọn dịch vụ");
            System.out.println("4. Hiển thị danh sách phòng");
            System.out.println("5. Thêm phòng mới");
            System.out.println("6. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    servicePhong.timKiemPhongTrong();
                    break;
                case "2":
                    System.out.println("Thanh toán phòng (Excel) - chưa triển khai");
                    break;
                case "3":
                    System.out.println("Lựa chọn dịch vụ (Excel) - chưa triển khai");
                    break;
                case "4":
                    servicePhong.searchRoomsExel();
                    break;
                case "5":
                    servicePhong.addRoomsExel();
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}