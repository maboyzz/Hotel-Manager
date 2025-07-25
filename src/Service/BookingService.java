package Service;

import DAO.CustomerDAO;
import DAO.BookingDAO;
import DAO.RoomDAO;
import Model.Customer;
import Model.DatPhong;
import Model.Room;
import Validator.DatPhongValidator;
import constant.TinhTrang;
import constant.TrangThai;
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
    static ArrayList<DatPhong> bookingList = new ArrayList<DatPhong>();
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

            if (room == null || !room.getTrangThai().equals(TinhTrang.PHONG_TRONG)) {
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
        DatPhong dp = new DatPhong();
        System.out.println("nhập gi chú :");
        dp.setGhiChu(sc.nextLine());
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setTrangThai(TrangThai.CHUA_THANH_TOAN);

        new BookingDAO().insertBooking(dp);
        new RoomDAO().updateRoomStatus(phongId, TinhTrang.DA_THUE);

        System.out.println("✅ Đặt phòng thành công!");
    }


    public void checkoutAndPaymentSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã phòng : ");
        Long id = sc.nextLong();
        DatPhong dp = new BookingDAO().findBookingById(id);
        if (dp == null) {
            System.out.println("Không tìm thấy thông tin đặt phòng!");
            return;
        }

        Room room = new RoomDAO().findRoomById(dp.getMaPhong());
        if (room == null) {
            System.out.println("Không tìm thấy phòng!");
            return;
        }
        Customer customer = new CustomerDAO().findCustomerById(dp.getMaKhachHang());
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng");
            return;
        }

        long soNgay = Duration.between(dp.getThoiGianDat(), dp.getThoiGianTra()).toDays();
        if (soNgay <= 0) soNgay = 1;

        long giaTien = room.getGiaPhong() * soNgay;

        new RoomDAO().updateRoomStatus(room.getID(), TinhTrang.PHONG_TRONG);
        new BookingDAO().updateBookingStatus(dp.getId(), TrangThai.DA_THANH_TOAN);
        System.out.println("Trả phòng thành công!");
        System.out.println("Khách thuê: " + customer.getTen() + " có cccd : " + customer.getCCCD());
        System.out.println("Phòng: " + room.getTenPhong());
        System.out.println("Số ngày thuê: " + soNgay);
        System.out.println("Thành tiền: " + giaTien + " VND");
    }

    public void showAllInvoicesSQL() {
        List<DatPhong> hoadons = new BookingDAO().findAllBookings();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        } else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }

    public void showUnpaidInvoicesSQL() {
        List<DatPhong> hoadons = new BookingDAO().findUnpaidBookings();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        } else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
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
            if (c.getCCCD().equalsIgnoreCase(cccd)) {
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
            if (room.getTrangThai().equals(TinhTrang.PHONG_TRONG)) {
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
                    if (r.getID().equals(phongId)) {
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

        DatPhong dp = new DatPhong();
        System.out.print("Nhập ghi chú: ");
        dp.setGhiChu(sc.nextLine());
        dp.setId(getMaxId() + 1);
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setTrangThai(TrangThai.CHUA_THANH_TOAN);

        bookingList.add(dp);

        // Cập nhật trạng thái phòng
        room.setTrangThai(TinhTrang.DA_THUE);

        System.out.println("✅ Đặt phòng thành công!");
    }

    public void checkoutAndPaymentExcel() {

        List<DatPhong> hoadons = new ArrayList<>();

        for (DatPhong dp : bookingList) {
            if (dp.getTrangThai().getDescription().equalsIgnoreCase("chưa thanh toán")) {
                hoadons.add(dp);
            }
        }

        if (!hoadons.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            DatPhong datPhong = null;
            Long bookingId = null;
            do {
                System.out.print("Nhập mã đặt phòng cần trả: ");
                try {
                    bookingId = Long.parseLong(sc.nextLine());
                    for (DatPhong dp : bookingList) {
                        if (dp.getId().equals(bookingId) && dp.getTrangThai().equals(TrangThai.CHUA_THANH_TOAN)) {
                            datPhong = dp;
                            break;
                        }
                    }
                    if (datPhong == null) {
                        System.out.println("Không tìm thấy thông tin đặt phòng!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Mã đặt phòng không hợp lệ! Nhập lại.");
                    return;
                }
            } while (datPhong == null);

            Room room = null;
            for (Room r : roomList) {
                if (r.getID().equals(datPhong.getMaPhong())) {
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
                if (c.getID().equals(datPhong.getMaKhachHang())) {
                    customer = c;
                    break;
                }
            }
            if (customer == null) {
                System.out.println("Không tìm thấy khách hàng!");
                return;
            }

            long soNgay = Duration.between(datPhong.getThoiGianDat(), datPhong.getThoiGianTra()).toDays();
            if (soNgay <= 0) soNgay = 1;
            long giaTien = room.getGiaPhong() * soNgay;

            // 6. Cập nhật trạng thái
            room.setTrangThai(TinhTrang.PHONG_TRONG);
            datPhong.setTrangThai(TrangThai.DA_THANH_TOAN);

            // 7. Hiển thị thông tin đúng
            System.out.println("HÓA ĐƠN THANH TOÁN");
            System.out.println("Khách thuê: " + customer.getTen() + " (CCCD: " + customer.getCCCD() + ")");
            System.out.println("Phòng      : " + room.getTenPhong() + " - Giá/ngày: " + room.getGiaPhong());
            System.out.println("Từ ngày    : " + datPhong.getThoiGianDat());
            System.out.println("Đến ngày   : " + datPhong.getThoiGianTra());
            System.out.println("Số ngày thuê: " + soNgay);
            System.out.println("Thành tiền : " + giaTien + " VND");
        }

    }


    public void showAllInvoicesExcel() {
        List<DatPhong> hoadons = new ArrayList<>();

        for (DatPhong dp : bookingList) {
            hoadons.add(dp);
        }

        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
            return;
        } else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }

    public void showUnpaidInvoicesExcel() {

        List<DatPhong> hoadons = new ArrayList<>();

        for (DatPhong dp : bookingList) {
            if (dp.getTrangThai().getDescription().equalsIgnoreCase("chưa thanh toán")) {
                hoadons.add(dp);
            }
        }

        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
            return;
        } else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }

    public void saveBookingListToExcel(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (DatPhong datPhong : bookingList) {
            data.add(new String[]{
                    String.valueOf(datPhong.getId()),
                    String.valueOf(datPhong.getMaKhachHang()),
                    String.valueOf(datPhong.getMaPhong()),
                    String.valueOf(datPhong.getThoiGianDat()),
                    String.valueOf(datPhong.getThoiGianTra()),
                    datPhong.getGhiChu(),
                    String.valueOf(datPhong.getTrangThai())

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

                DatPhong datPhong = new DatPhong();
                datPhong.setId(getLongValue(row.getCell(0)));
                datPhong.setMaKhachHang(getLongValue(row.getCell(1)));
                datPhong.setMaPhong(getLongValue(row.getCell(2)));

                String thoiGianDatStr = row.getCell(3).getStringCellValue();
                String thoiGianTraStr = row.getCell(4).getStringCellValue();
                datPhong.setThoiGianDat(LocalDateTime.parse(thoiGianDatStr));
                datPhong.setThoiGianTra(LocalDateTime.parse(thoiGianTraStr));

                datPhong.setGhiChu(row.getCell(5).getStringCellValue());

                String trangThaiStr = row.getCell(6).getStringCellValue();
                datPhong.setTrangThai(TrangThai.valueOf(trangThaiStr)); // Enum
                bookingList.add(datPhong);

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
        for (DatPhong datPhong : bookingList) {
            if (datPhong != null && datPhong.getId() > maxId) {
                maxId = datPhong.getId();
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
