import Model.Room;
import Service.ServiceRoom;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceRoom serviceDatPhong = new ServiceRoom();
        Scanner sc = new Scanner(System.in);
        String filePath = "output.xlsx";
        boolean isExit = false;

        // Thêm ShutdownHook để lưu danh sách phòng vào Excel khi thoát chương trình
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serviceDatPhong.luuDanhSachPhong(filePath);
            System.out.println("Danh sách phòng đã được lưu vào " + filePath);
        }));

        while (!isExit) {
            displayMainMenu();
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSQLMenu(serviceDatPhong, sc);
                    break;
                case "2":
                    handleExcelMenu(serviceDatPhong, sc);
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
        System.out.println("3. Lựa chọn dịch vụ");
        System.out.println("4. Hiển thị danh sách phòng");
        System.out.println("5. Thêm phòng mới");
        System.out.println("6. Quay lại");
    }

    private static void handleSQLMenu(ServiceRoom service, Scanner sc) {
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
                        service.timPhongtrong();
                        break;
                    case 2:
                        System.out.println("Thuê phòng (SQL)");
                        // Gọi hàm thanh toán phòng
                        break;
                    case 3:
                        System.out.println("Lựa chọn dịch vụ (SQL)");
                        // Gọi hàm lựa chọn dịch vụ
                        break;
                    case 4:
                        System.out.println("Hiển thị danh sách phòng (SQL)");
                        service.timToanBoPhong();
                        break;
                    case 5:
                        System.out.println("Thêm phòng mới (SQL)");
                        service.addRoomsSQL();
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

    private static void handleExcelMenu(ServiceRoom service, Scanner sc) {
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
                        service.timKiemPhongTrong();
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
                        service.searchRoomsExel();
                        break;
                    case 5:
                        System.out.println("Thêm phòng mới (Excel)");
                        service.addRoomsExel();
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