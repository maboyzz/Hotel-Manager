import Service.ServiceCustomer;
import Service.ServiceDatPhong;
import Service.ServiceRoom;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceRoom servicePhong = new ServiceRoom();
        ServiceCustomer serviceCustomer = new ServiceCustomer();
        ServiceDatPhong serviceDatPhong = new ServiceDatPhong();

        Scanner sc = new Scanner(System.in);

        String folderPath = "dulieu"; // hoặc tuyệt đối như "C:/Users/TenUser/Desktop/dulieu"
        File folder = new File(folderPath);

        // Tạo thư mục nếu chưa có
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePathPhong = folderPath + File.separator + "phongOutput.xlsx";
        String filePathKhachHang = folderPath + File.separator + "khachHangOutput.xlsx";
        String filePathDatPhong = folderPath + File.separator + "datPhongOutput.xlsx";
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            servicePhong.luuDanhSachPhong(filePathPhong);
            serviceCustomer.luuDanhSachKhachHang(filePathKhachHang);
            serviceDatPhong.luuDanhSachDatPhong(filePathDatPhong);
        }));
        if (new File(filePathDatPhong).exists()) {
            serviceDatPhong.docDanhSachDatPhongTuFileExcel(filePathDatPhong);
        }
        if (new File(filePathPhong).exists()) {
            servicePhong.docDanhSachPhongTuFileExcel(filePathPhong);
        }
        if (new File(filePathKhachHang).exists()) {
            serviceCustomer.docDanhSachKhachHangTuFileExcel(filePathKhachHang);
        }
        boolean isExit = false;

        // Tự động lưu file Excel khi thoát chương trình


        while (!isExit) {
            displayMainMenu();
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSQLMenu(serviceDatPhong, serviceCustomer, servicePhong, sc);
                    break;
                case "2":
                    handleExelMenu(serviceDatPhong, serviceCustomer, servicePhong, sc);

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
                    handleRoomMenuSQL(servicePhong, sc);
                    break;
                case "2":
                    handleCustomerMenuSQL(serviceCustomer, sc);
                    break;
                case "3":
                    handleBookingMenuSQL(serviceDatPhong, sc);
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

    private static void handleExelMenu(ServiceDatPhong serviceDatPhong, ServiceCustomer serviceCustomer, ServiceRoom servicePhong, Scanner sc) {
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
                    handleRoomMenuExel(servicePhong, sc);
                    break;
                case "2":
                    handleCustomerMenuExel(serviceCustomer, sc);
                    break;
                case "3":
                    handleBookingMenuExel(serviceDatPhong, sc);
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

    private static void handleRoomMenuSQL(ServiceRoom servicePhong, Scanner sc) {
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

    private static void handleRoomMenuExel(ServiceRoom servicePhong, Scanner sc) {
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
                    servicePhong.timKiemPhongTrongExel();
                    break;
                case "2":
                    servicePhong.searchRoomsExel();
                    break;
                case "3":
                    servicePhong.addRoomsExel();
                    break;
                case "4":
                    servicePhong.searchRoomsExel();
                    servicePhong.xoaPhongTheoIDExel();
                    break;
                case "5":
                    servicePhong.searchRoomsExel();
                    System.out.println("đang trieern khai");
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleCustomerMenuSQL(ServiceCustomer serviceCustomer, Scanner sc) {
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

    private static void handleCustomerMenuExel(ServiceCustomer serviceCustomer, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Quản lý Khách hàng ---");
            System.out.println("1. Tìm kiếm khách hàng");
            System.out.println("2. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    serviceCustomer.searchCustomersExel();
                    break;
                case "2":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }


    private static void handleBookingMenuSQL(ServiceDatPhong serviceDatPhong, Scanner sc) {
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
                    serviceDatPhong.taoKhachVaDatPhongSQL();
                    break;
                case "2":
                    serviceDatPhong.hienThiTatCaHoaDonSQL();
                    break;
                case "3":
                    serviceDatPhong.hienThiTatCaHoaDonChuaThanhToanSQL();
                    serviceDatPhong.traPhongVaThanhToanSQL();

                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleBookingMenuExel(ServiceDatPhong serviceDatPhong, Scanner sc) {
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
                    serviceDatPhong.taoKhachVaDatPhongExel();
                    break;
                case "2":
                    serviceDatPhong.hienThiTatCaHoaDonExel();
                    break;
                case "3":
                    serviceDatPhong.hienThiTatCaHoaDonChuaThanhToanExel();
                    serviceDatPhong.traPhongVaThanhToanExel();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

}