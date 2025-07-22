package Service;

import DAO.CustomerDAO;
import DAO.DatPhongDAO;
import DAO.RoomDAO;
import Model.Customer;
import Model.DatPhong;
import Model.Room;
import constant.TinhTrang;

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
        Customer customer = serviceCustomer.addCustomerSQL(); // Phương thức tự viết
        Long khachHangId = new CustomerDAO().insertCustomer(customer); // Trả về ID sau khi insert

        List<Room> phongTrong = new RoomDAO().TimPhongTrong();
        if (phongTrong.isEmpty()) {
            System.out.println(" Không có phòng trống.");
            return;
        }

        System.out.println("Các phòng trống:");
        for (Room p : phongTrong) {
            System.out.println(p);
        }

        System.out.print("Nhập mã phòng muốn đặt: ");
        Long phongId = Long.parseLong(sc.nextLine());
        Room room = new RoomDAO().findRoomById(phongId);

        if (room == null || !room.getTrangThai().equals(TinhTrang.PHONG_TRONG)) {
            System.out.println(" Phòng không hợp lệ.");
            return;
        }
        System.out.print("Nhập ngày trả (yyyy-MM-dd): ");
        String ngayTraStr = sc.nextLine();
        LocalDateTime ngayNhan = LocalDateTime.now();
        LocalDateTime ngayTra;
        try {
            ngayTra = LocalDate.parse(ngayTraStr).atStartOfDay();
            if (!ngayTra.isAfter(ngayNhan)) {
                System.out.println("Ngày trả phải sau ngày nhận (ngày hiện tại).");
                return;
            }
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ!");
            return;
        }

        DatPhong dp = new DatPhong();
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setGhiChu("");

        new DatPhongDAO().datPhong(dp);
        new RoomDAO().capNhatTrangThai(phongId, TinhTrang.DA_THUE);

        System.out.println("Đặt phòng thành công!");
    }
    public void traPhongVaThanhToan(){

    }

}
