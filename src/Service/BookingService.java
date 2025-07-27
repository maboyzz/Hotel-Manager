package Service;

import DAO.CustomerDAO;
import DAO.BookingDAO;
import DAO.RoomDAO;
import Model.Customer;
import Model.Booking;
import Model.Room;
import Validator.DatPhongValidator;
import constant.RoomStatus;
import constant.BookingStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static DAO.ExportToExcel.exportToExcel;
import static Service.CustomerService.customerList;
import static Service.RoomService.roomList;

public class BookingService {
    static ArrayList<Booking> bookingList = new ArrayList<Booking>();
    CustomerService customerService = new CustomerService();


    public void createCustomerAndBookingSQL() {
        Scanner sc = new Scanner(System.in);
        Customer customer = customerService.addCustomerSQL();
        Long khachHangId = new CustomerDAO().insertCustomer(customer);

        List<Room> phongTrong = new RoomDAO().findAvailableRooms();
        if (phongTrong.isEmpty()) {
            System.out.println("Không có phòng trống.");
            return;
        }

        System.out.println("Các phòng trống:");
        for (Room p : phongTrong) {
            System.out.println(p);
        }

        Long phongId = null;
        Room room = null;

        // Nhập lại mã phòng cho đến khi hợp lệ
        do {
            System.out.print("Nhập mã phòng muốn đặt: ");
            try {
                phongId = Long.parseLong(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Mã phòng không hợp lệ! Hãy nhập lại.");
                continue;
            }

            room = new RoomDAO().findRoomById(phongId);

            if (room == null || !room.getStatus().equals(RoomStatus.AVAILABLE)) {
                System.out.println("Phòng không tồn tại hoặc không trống. Nhập lại.");
                phongId = null;
            }
        } while (phongId == null);


        // Nhập lại ngày trả cho đến khi hợp lệ
        LocalDateTime ngayNhan = LocalDateTime.now();
        LocalDateTime ngayTra = null;

        do {
            System.out.print("Nhập ngày trả (yyyy-MM-dd): ");
            String ngayTraStr = sc.nextLine();

            try {
                ngayTra = LocalDate.parse(ngayTraStr).atStartOfDay();
                if (!DatPhongValidator.isValidNgayTra(ngayNhan, ngayTra)) {
                    ngayTra = null;
                }
            } catch (Exception e) {
                System.out.println("Định dạng ngày không hợp lệ! Nhập lại.");
            }
        } while (ngayTra == null);


        // Đặt phòng
        Booking dp = new Booking();
        System.out.println("nhập gi chú :");
        dp.setNote(sc.nextLine());
        dp.setCustomerId(khachHangId);
        dp.setRoomId(phongId);
        dp.setCheckInTime(ngayNhan);
        dp.setCheckOutTime(ngayTra);
        dp.setStatus(BookingStatus.UNPAID);

        new BookingDAO().insertBooking(dp);
        new RoomDAO().updateRoomStatus(phongId, RoomStatus.OCCUPIED);

        System.out.println("✅ Đặt phòng thành công!");
    }


    public void checkoutAndPaymentSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã phòng : ");
        Long id = sc.nextLong();
        Booking dp = new BookingDAO().findBookingById(id);
        if (dp == null) {
            System.out.println("Không tìm thấy thông tin đặt phòng!");
            return;
        }

        Room room = new RoomDAO().findRoomById(dp.getRoomId());
        if (room == null) {
            System.out.println("Không tìm thấy phòng!");
            return;
        }
        Customer customer = new CustomerDAO().findCustomerById(dp.getCustomerId());
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng");
            return;
        }

        long soNgay = Duration.between(dp.getCheckInTime(), dp.getCheckOutTime()).toDays();
        if (soNgay <= 0) soNgay = 1;

        long giaTien = room.getPrice() * soNgay;

        new RoomDAO().updateRoomStatus(room.getId(), RoomStatus.AVAILABLE);
        new BookingDAO().updateBookingStatus(dp.getId(), BookingStatus.PAID);
        System.out.println("Trả phòng thành công!");
        System.out.println("Khách thuê: " + customer.getName() + " có cccd : " + customer.getCitizenId());
        System.out.println("Phòng: " + room.getRoomName());
        System.out.println("Số ngày thuê: " + soNgay);
        System.out.println("Thành tiền: " + giaTien + " VND");
    }

    public void showAllInvoicesSQL() {
        List<Booking> hoadons = new BookingDAO().findAllBookings();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        } else {
            printHeader();
            for (Booking dp : hoadons) {
                System.out.println(dp);
            }
            System.out.println("+------+------------+----------+---------------------+---------------------+--------------------------------+---------------+");

        }
    }

    public void showUnpaidInvoicesSQL() {
        List<Booking> hoadons = new BookingDAO().findUnpaidBookings();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        } else {
            printHeader();
            for (Booking dp : hoadons) {
                System.out.println(dp);
            }
            System.out.println("+------+------------+----------+---------------------+---------------------+--------------------------------+---------------+");
        }
    }

    public void createCustomerAndBookingExcel() {
        Scanner sc = new Scanner(System.in);

        // 1. Nhập CCCD trước
        System.out.print("Nhập CCCD khách hàng: ");
        String cccd = sc.nextLine().trim();

        Customer customer = null;
        // 2. Kiểm tra trong customerList
        for (Customer c : customerList) {
            if (c.getCitizenId().equalsIgnoreCase(cccd)) {
                customer = c;
                break;
            }
        }

        if (customer != null) {
            System.out.println("Khách hàng đã tồn tại, sử dụng lại ID: " + customer.getID());
        } else {
            // Nếu chưa có, nhập thông tin mới
            customer = CustomerService.inputCustomerInfoCCCD();
            customer.setID(getMaxId() + 1);
            customerList.add(customer);
            System.out.println("Đã thêm khách hàng mới: " + customer);
        }

        Long khachHangId = customer.getID();


        List<Room> phongTrong = new ArrayList<>();
        for (Room room : roomList) {
            if (room.getStatus().equals(RoomStatus.AVAILABLE)) {
                phongTrong.add(room);
            }
        }
        if (phongTrong.isEmpty()) {
            System.out.println("Hiện tại không có phòng trống.");
            return;
        }

        System.out.println("Các phòng trống:");
        phongTrong.forEach(System.out::println);

        Long phongId = null;
        Room room = null;

        do {
            System.out.print("Nhập mã phòng muốn đặt: ");
            try {
                phongId = Long.parseLong(sc.nextLine());
                room = null;

                // Duyệt danh sách phòng trống để tìm phòng theo ID
                for (Room r : phongTrong) {
                    if (r.getId().equals(phongId)) {
                        room = r;
                        break;
                    }
                }

                if (room == null) {
                    System.out.println("Phòng không tồn tại hoặc không trống. Nhập lại.");
                    phongId = null;
                }

            } catch (Exception e) {
                System.out.println("Mã phòng không hợp lệ! Hãy nhập lại.");
                phongId = null;
            }
        } while (phongId == null);

        LocalDateTime ngayNhan = LocalDateTime.now();
        LocalDateTime ngayTra = null;

        do {
            System.out.print("Nhập ngày trả (yyyy-MM-dd): ");
            String ngayTraStr = sc.nextLine();
            try {
                ngayTra = LocalDate.parse(ngayTraStr).atStartOfDay();
                if (!DatPhongValidator.isValidNgayTra(ngayNhan, ngayTra)) {
                    ngayTra = null;
                }
            } catch (Exception e) {
                System.out.println("Định dạng ngày không hợp lệ! Nhập lại.");
            }
        } while (ngayTra == null);

        Booking dp = new Booking();
        System.out.print("Nhập ghi chú: ");
        dp.setNote(sc.nextLine());
        dp.setId(getMaxId() + 1);
        dp.setCustomerId(khachHangId);
        dp.setRoomId(phongId);
        dp.setCheckInTime(ngayNhan);
        dp.setCheckOutTime(ngayTra);
        dp.setStatus(BookingStatus.UNPAID);

        bookingList.add(dp);

        // Cập nhật trạng thái phòng
        room.setStatus(RoomStatus.OCCUPIED);

        System.out.println("✅ Đặt phòng thành công!");
    }

    public void checkoutAndPaymentExcel() {

        List<Booking> hoadons = new ArrayList<>();

        for (Booking dp : bookingList) {
            if (dp.getStatus().getDescription().equalsIgnoreCase("chưa thanh toán")) {
                hoadons.add(dp);
            }
        }

        if (!hoadons.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            Booking booking = null;
            Long bookingId = null;
            do {
                System.out.print("Nhập mã đặt phòng cần trả: ");
                try {
                    bookingId = Long.parseLong(sc.nextLine());
                    for (Booking dp : bookingList) {
                        if (dp.getId().equals(bookingId) && dp.getStatus().equals(BookingStatus.UNPAID)) {
                            booking = dp;
                            break;
                        }
                    }
                    if (booking == null) {
                        System.out.println("Không tìm thấy thông tin đặt phòng!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Mã đặt phòng không hợp lệ! Nhập lại.");
                    return;
                }
            } while (booking == null);

            Room room = null;
            for (Room r : roomList) {
                if (r.getId().equals(booking.getRoomId())) {
                    room = r;
                    break;
                }
            }
            if (room == null) {
                System.out.println("Không tìm thấy phòng!");
                return;
            }

            Customer customer = null;
            for (Customer c : customerList) {
                if (c.getID().equals(booking.getCustomerId())) {
                    customer = c;
                    break;
                }
            }
            if (customer == null) {
                System.out.println("Không tìm thấy khách hàng!");
                return;
            }

            long soNgay = Duration.between(booking.getCheckInTime(), booking.getCheckOutTime()).toDays();
            if (soNgay <= 0) soNgay = 1;
            long giaTien = room.getPrice() * soNgay;

            // 6. Cập nhật trạng thái
            room.setStatus(RoomStatus.AVAILABLE);
            booking.setStatus(BookingStatus.PAID);

            // 7. Hiển thị thông tin đúng
            System.out.println("HÓA ĐƠN THANH TOÁN");
            System.out.println("Khách thuê: " + customer.getName() + " (CCCD: " + customer.getCitizenId() + ")");
            System.out.println("Phòng      : " + room.getRoomName() + " - Giá/ngày: " + room.getPrice());
            System.out.println("Từ ngày    : " + booking.getCheckInTime());
            System.out.println("Đến ngày   : " + booking.getCheckOutTime());
            System.out.println("Số ngày thuê: " + soNgay);
            System.out.println("Thành tiền : " + giaTien + " VND");
        }

    }


    public void showAllInvoicesExcel() {
        if (bookingList.isEmpty()) {
            System.out.println("Không có hóa đơn.");
            return;
        }

        printHeader();
        for (Booking dp : bookingList) {
            System.out.println(dp);
        }
        System.out.println("+------+------------+----------+---------------------+---------------------+--------------------------------+---------------+");
    }

    public void showUnpaidInvoicesExcel() {

        List<Booking> hoadons = new ArrayList<>();

        for (Booking dp : bookingList) {
            if (dp.getStatus().getDescription().equalsIgnoreCase("chưa thanh toán")) {
                hoadons.add(dp);
            }
        }

        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
            return;
        } else {
            printHeader();
            for (Booking dp : hoadons) {
                System.out.println(dp);
            }
            System.out.println("+------+------------+----------+---------------------+---------------------+--------------------------------+---------------+");

        }
    }
    public void printHeader() {
        System.out.println("+------+------------+----------+---------------------+---------------------+--------------------------------+---------------+");
        System.out.printf("| %-4s | %-10s | %-8s | %-19s | %-19s | %-30s | %-10s    |\n",
                "ID", "Khách", "Phòng", "Thời gian đặt", "Thời gian trả", "Ghi chú", "Trạng thái");
        System.out.println("+------+------------+----------+---------------------+---------------------+-----------------------------------+------------+");
    }

    public void saveBookingListToExcel(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (Booking booking : bookingList) {
            data.add(new String[]{
                    String.valueOf(booking.getId()),
                    String.valueOf(booking.getCustomerId()),
                    String.valueOf(booking.getRoomId()),
                    String.valueOf(booking.getCheckInTime()),
                    String.valueOf(booking.getCheckOutTime()),
                    booking.getNote(),
                    String.valueOf(booking.getStatus())

            });
        }
        String[] headers = {"ID Đặt Hàng", "ID Khách Hàng", "ID Phòng", "Ngày nhận", "Ngày trả", "Gi chú", " Trạng Thái"};
        exportToExcel(data, headers, filePath);
        System.out.println("Danh sách đặt phòng đã được lưu vào file: " + filePath);
    }

    public void loadBookingListFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên
            bookingList.clear(); // Xóa dữ liệu cũ trước khi load

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề (i = 1)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Booking booking = new Booking();
                booking.setId(getLongValue(row.getCell(0)));
                booking.setCustomerId(getLongValue(row.getCell(1)));
                booking.setRoomId(getLongValue(row.getCell(2)));

                String thoiGianDatStr = row.getCell(3).getStringCellValue();
                String thoiGianTraStr = row.getCell(4).getStringCellValue();
                booking.setCheckInTime(LocalDateTime.parse(thoiGianDatStr));
                booking.setCheckOutTime(LocalDateTime.parse(thoiGianTraStr));

                booking.setNote(row.getCell(5).getStringCellValue());

                String trangThaiStr = row.getCell(6).getStringCellValue();
                booking.setStatus(BookingStatus.valueOf(trangThaiStr)); // Enum
                bookingList.add(booking);

            }

            System.out.println("ĐP Đã import danh sách đặt phòng từ file Excel.");
        } catch (IOException e) {
            System.out.println("ĐP Lỗi đọc file Excel: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ĐP Lỗi định dạng dữ liệu trong file Excel: " + e.getMessage());
        }
    }

    private Long getMaxId() {
        if (bookingList == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (Booking booking : bookingList) {
            if (booking != null && booking.getId() > maxId) {
                maxId = booking.getId();
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

}
