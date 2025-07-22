package Service;

import DAO.CustomerDAO;
import DAO.DatPhongDAO;
import DAO.RoomDAO;
import Model.Customer;
import Model.DatPhong;
import Model.Room;
import Validator.DatPhongValidator;
import constant.TinhTrang;
import constant.TrangThai;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceDatPhong {
    ArrayList<DatPhong> listDatPhong = new ArrayList<DatPhong>();
    ServiceCustomer serviceCustomer = new ServiceCustomer();


    public void taoKhachVaDatPhong() {
        Scanner sc = new Scanner(System.in);
        Customer customer = serviceCustomer.addCustomerSQL(); // Tạo khách hàng
        Long khachHangId = new CustomerDAO().insertCustomer(customer);

        List<Room> phongTrong = new RoomDAO().TimPhongTrong();
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
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setGhiChu("");
        dp.setTrangThai(TrangThai.CHUA_THANH_TOAN);

        new DatPhongDAO().datPhong(dp);
        new RoomDAO().capNhatTrangThai(phongId, TinhTrang.DA_THUE);

        System.out.println("✅ Đặt phòng thành công!");
    }


    public void traPhongVaThanhToan(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã phòng : ");
        Long id = sc.nextLong();
        DatPhong dp = new DatPhongDAO().timDatPhongBangID(id);
        if (dp == null) {
            System.out.println("Không tìm thấy thông tin đặt phòng!");
            return;
        }

        Room room = new RoomDAO().findRoomById(dp.getMaPhong());
        if (room == null) {
            System.out.println("Không tìm thấy phòng!");
            return;
        }
        Customer customer = new CustomerDAO().selectCustomerById(dp.getMaKhachHang());
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng");
            return;
        }

        long soNgay = Duration.between(dp.getThoiGianDat(), dp.getThoiGianTra()).toDays();
        if (soNgay <= 0) soNgay = 1;

        Long giaTien = room.getGiaPhong() * soNgay;

        new RoomDAO().capNhatTrangThai(room.getID(), TinhTrang.PHONG_TRONG);
        new DatPhongDAO().capNhatTrangThai(dp.getId(), TrangThai.DA_THANH_TOAN);
        System.out.println("Trả phòng thành công!");
        System.out.println("Khách thuê: " + customer.getTen()+ " có cccd : "+customer.getCCCD());
        System.out.println("Phòng: " + room.getTenPhong());
        System.out.println("Số ngày thuê: " + soNgay);
        System.out.println("Thành tiền: " + giaTien + " VND");
    }

    public void hienThiTatCaHoaDon(){
        List<DatPhong> hoadons = new DatPhongDAO().timTatCaHoaDon();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
    public void hienThiTatCaHoaDonChuaThanhToan(){
        List<DatPhong> hoadons = new DatPhongDAO().timTatCaHoaDonChuaThanhToan();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
}
