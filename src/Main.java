import Service.CustomerService;
import Service.BookingService;
import Service.RoomService;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RoomService servicePhong = new RoomService();
        CustomerService customerService = new CustomerService();
        BookingService bookingService = new BookingService();

        Scanner sc = new Scanner(System.in);

        String folderPath = "dulieu";
        File folder = new File(folderPath);


        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePathPhong = folderPath + File.separator + "phongOutput.xlsx";
        String filePathKhachHang = folderPath + File.separator + "khachHangOutput.xlsx";
        String filePathDatPhong = folderPath + File.separator + "datPhongOutput.xlsx";
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            servicePhong.saveRoomListToExcel(filePathPhong);
            customerService.saveCustomerListToExcel(filePathKhachHang);
            bookingService.saveBookingListToExcel(filePathDatPhong);
        }));
        if (new File(filePathDatPhong).exists()) {
            bookingService.loadBookingListFromExcel(filePathDatPhong);
        }
        if (new File(filePathPhong).exists()) {
            servicePhong.loadRoomListFromExcel(filePathPhong);
        }
        if (new File(filePathKhachHang).exists()) {
            customerService.loadCustomerListFromExcel(filePathKhachHang);
        }
        boolean isExit = false;



        while (!isExit) {
            displayMainMenu();
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSQLMenu(bookingService, customerService, servicePhong, sc);
                    break;
                case "2":
                    handleExelMenu(bookingService, customerService, servicePhong, sc);

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
        System.out.println("\n+--------------------------------+");
        System.out.println("|       === Hotel Manager ===    |");
        System.out.println("+--------------------------------+");
        System.out.printf("| %-30s |\n", "1. Quản lý Phòng SQL");
        System.out.printf("| %-30s |\n", "2. Quản lý Phòng Excel");
        System.out.printf("| %-30s |\n", "3. Thoát");
        System.out.println("+--------------------------------+");
    }

    private static void handleSQLMenu(BookingService bookingService, CustomerService customerService, RoomService servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|             === Quản lý SQL ===            |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Quản lý Phòng");
            System.out.printf("| %-42s |\n", "2. Quản lý Khách hàng");
            System.out.printf("| %-42s |\n", "3. Quản lý Đặt phòng / Thanh toán");
            System.out.printf("| %-42s |\n", "4. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    handleRoomMenuSQL(servicePhong, sc);
                    break;
                case "2":
                    handleCustomerMenuSQL(customerService, sc);
                    break;
                case "3":
                    handleBookingMenuSQL(bookingService, sc);
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

    private static void handleExelMenu(BookingService bookingService, CustomerService customerService, RoomService servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|             === Quản lý EXCEL ===          |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Quản lý Phòng");
            System.out.printf("| %-42s |\n", "2. Quản lý Khách hàng");
            System.out.printf("| %-42s |\n", "3. Quản lý Đặt phòng / Thanh toán");
            System.out.printf("| %-42s |\n", "4. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    handleRoomMenuExel(servicePhong, sc);
                    break;
                case "2":
                    handleCustomerMenuExel(customerService, sc);
                    break;
                case "3":
                    handleBookingMenuExel(bookingService, sc);
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

    private static void handleRoomMenuSQL(RoomService servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|           --- Quản lý Phòng SQL ---        |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Tìm kiếm phòng trống");
            System.out.printf("| %-42s |\n", "2. Hiển thị tất cả phòng");
            System.out.printf("| %-42s |\n", "3. Thêm phòng mới");
            System.out.printf("| %-42s |\n", "4. Cập nhật phòng");
            System.out.printf("| %-42s |\n", "5. Xóa phòng");
            System.out.printf("| %-42s |\n", "6. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    servicePhong.findAvailableRoomsSQL();
                    break;
                case "2":
                    servicePhong.findAllRoomsSQL();
                    break;
                case "3":
                    servicePhong.addRoomSQL();
                    break;
                case "4":
                    servicePhong.findAllRoomsSQL();
                    servicePhong.updateRoomSQL();
                    break;
                case "5":
                    servicePhong.findAllRoomsSQL();
                    servicePhong.deleteRoomSQL();
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleRoomMenuExel(RoomService servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|           --- Quản lý Phòng EXCEL---        |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Tìm kiếm phòng trống");
            System.out.printf("| %-42s |\n", "2. Hiển thị tất cả phòng");
            System.out.printf("| %-42s |\n", "3. Thêm phòng mới");
            System.out.printf("| %-42s |\n", "4. Cập nhật phòng");
            System.out.printf("| %-42s |\n", "5. Xóa phòng");
            System.out.printf("| %-42s |\n", "6. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    servicePhong.findAvailableRoomsExcel();
                    break;
                case "2":
                    servicePhong.findAllRoomsExcel();
                    break;
                case "3":
                    servicePhong.addRoomExcel();
                    break;
                case "4":
                    servicePhong.findAllRoomsExcel();
                    servicePhong.updateRoomByIdExcel();
                    break;
                case "5":
                    servicePhong.findAllRoomsExcel();
                    servicePhong.deleteRoomByIdExcel();

                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleCustomerMenuSQL(CustomerService customerService, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|        --- Quản lý Khách hàng SQL---       |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Hiển thị toàn bộ khách hàng");
            System.out.printf("| %-42s |\n", "2. Cập nhật khách hàng");
            System.out.printf("| %-42s |\n", "3. Xóa khách hàng");
            System.out.printf("| %-42s |\n", "4. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    customerService.findAllCustomersSQL();
                    break;
                case "2":
                    customerService.findAllCustomersSQL();
                    customerService.updateCustomerByIdSQL();
                    break;
                case "3":
                    customerService.findAllCustomersSQL();
                    customerService.deleteCustomerByIdSQL();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleCustomerMenuExel(CustomerService customerService, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|      --- Quản lý Khách hàng EXCEL---       |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Hiển thị toàn bộ khách hàng");
            System.out.printf("| %-42s |\n", "2. Cập nhật khách hàng");
            System.out.printf("| %-42s |\n", "3. Xóa khách hàng");
            System.out.printf("| %-42s |\n", "4. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    customerService.findAllCustomersExcel();
                    break;
                case "2":
                    customerService.findAllCustomersExcel();
                    customerService.updateCustomerByIdExcel();
                    break;
                case "3":
                    customerService.findAllCustomersExcel();
                    customerService.deleteCustomerByIdExcel();
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }


    private static void handleBookingMenuSQL(BookingService bookingService, Scanner sc) {
        boolean back = false;
        while (!back) {
                System.out.println("\n+--------------------------------------------+");
                System.out.println("|    --- Quản lý Đặt phòng & Thanh toán ---  |");
                System.out.println("+--------------------------------------------+");
                System.out.printf("| %-42s |\n", "1. Tạo đặt phòng");
                System.out.printf("| %-42s |\n", "2. Xem danh sách hóa đơn");
                System.out.printf("| %-42s |\n", "3. Trả phòng và thanh toán");
                System.out.printf("| %-42s |\n", "4. Quay lại");
                System.out.println("+--------------------------------------------+");
                System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    bookingService.createCustomerAndBookingSQL();
                    break;
                case "2":
                    bookingService.showAllInvoicesSQL();
                    break;
                case "3":
                    bookingService.showUnpaidInvoicesSQL();
                    bookingService.checkoutAndPaymentSQL();

                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void handleBookingMenuExel(BookingService bookingService, Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n+--------------------------------------------+");
            System.out.println("|    --- Quản lý Đặt phòng & Thanh toán ---  |");
            System.out.println("+--------------------------------------------+");
            System.out.printf("| %-42s |\n", "1. Tạo đặt phòng");
            System.out.printf("| %-42s |\n", "2. Xem danh sách hóa đơn");
            System.out.printf("| %-42s |\n", "3. Trả phòng và thanh toán");
            System.out.printf("| %-42s |\n", "4. Quay lại");
            System.out.println("+--------------------------------------------+");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    bookingService.createCustomerAndBookingExcel();
                    break;
                case "2":
                    bookingService.showAllInvoicesExcel();
                    break;
                case "3":
                    bookingService.showUnpaidInvoicesExcel();
                    bookingService.checkoutAndPaymentExcel();
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