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

        // Thêm ShutdownHook để lưu danh sách phòng vào Excel khi thoát chương trình
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

    private static void displaySubMenu() {
        System.out.println("\n=== Quản Lý Phòng ===");
        System.out.println("1. Tìm kiếm phòng trống");
        System.out.println("2. Thuê phòng");
        System.out.println("3. Xem thông tin khách hàng");
        System.out.println("4. Hiển thị danh sách phòng");
        System.out.println("5. Thêm phòng mới");
        System.out.println("6. Quay lại");
    }

    private static void handleSQLMenu(ServiceDatPhong serviceDatPhong, ServiceCustomer serviceCustomer, ServiceRoom servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            displaySubMenu();
            System.out.print("Nhập lựa chọn: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); // Xóa bộ đệm
                switch (choice) {
                    case 1:
                        System.out.println("Tìm kiếm phòng trống (SQL)");
                        servicePhong.timPhongtrong();
                        break;
                    case 2:
                        System.out.println("Thuê phòng (SQL)");
                        serviceDatPhong.taoKhachVaDatPhong();

                        break;
                    case 3:
                        System.out.println("Xem thông tin khách hàng (SQL)");
                        serviceCustomer.searchCustomerSQL();
                        break;
                    case 4:
                        System.out.println("Hiển thị danh sách phòng (SQL)");
                        servicePhong.timToanBoPhong();
                        break;
                    case 5:
                        System.out.println("Thêm phòng mới (SQL)");
                        servicePhong.addRoomsSQL();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                        break;
                }
            } else {
                System.out.println("Vui lòng nhập một số!");
                sc.nextLine(); // Xóa input không hợp lệ
            }
        }
    }

    private static void handleExcelMenu(ServiceCustomer serviceCustomer,ServiceRoom servicePhong, Scanner sc) {
        boolean back = false;
        while (!back) {
            displaySubMenu();
            System.out.print("Nhập lựa chọn: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); // Xóa bộ đệm
                switch (choice) {
                    case 1:
                        System.out.println("Tìm kiếm phòng trống (Excel)");
                        servicePhong.timKiemPhongTrong();
                        break;
                    case 2:
                        System.out.println("Thanh toán phòng (Excel)");
                        // Gọi hàm thanh toán phòng
                        break;
                    case 3:
                        System.out.println("Lựa chọn dịch vụ (Excel)");
                        // Gọi hàm lựa chọn dịch vụ
                        break;
                    case 4:
                        System.out.println("Hiển thị danh sách phòng (Excel)");
                        servicePhong.searchRoomsExel();
                        break;
                    case 5:
                        System.out.println("Thêm phòng mới (Excel)");
                        servicePhong.addRoomsExel();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                        break;
                }
            } else {
                System.out.println("Vui lòng nhập một số!");
                sc.nextLine(); // Xóa input không hợp lệ
            }
        }
    }
}