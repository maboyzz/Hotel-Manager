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

import static DAO.ExportToExcel.exportToExcel;
import static Service.ServiceCustomer.listCustomer;
import static Service.ServiceRoom.listRoom;

public class ServiceDatPhong {
    ArrayList<DatPhong> listDatPhong = new ArrayList<DatPhong>();
    ServiceCustomer serviceCustomer = new ServiceCustomer();


    public void taoKhachVaDatPhongSQL() {
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
        System.out.println("nhập gi chú :");
        dp.setGhiChu(sc.nextLine());
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setTrangThai(TrangThai.CHUA_THANH_TOAN);

        new DatPhongDAO().datPhong(dp);
        new RoomDAO().capNhatTrangThai(phongId, TinhTrang.DA_THUE);

        System.out.println("✅ Đặt phòng thành công!");
    }


    public void traPhongVaThanhToanSQL(){
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

    public void hienThiTatCaHoaDonSQL(){
        List<DatPhong> hoadons = new DatPhongDAO().timTatCaHoaDon();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
    public void hienThiTatCaHoaDonChuaThanhToanSQL(){
        List<DatPhong> hoadons = new DatPhongDAO().timTatCaHoaDonChuaThanhToan();
        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
    public void taoKhachVaDatPhongExel() {
        Scanner sc = new Scanner(System.in);
        Customer customer = ServiceCustomer.addCustomerExel(); // Tạo khách hàng
        Long khachHangId = customer.getID();
        List<Room> danhSachTatCaPhong = listRoom;
        List<Room> phongTrong = new ArrayList<>();

        for (Room room : danhSachTatCaPhong) {
            if (room.getTrangThai().getDescription().equalsIgnoreCase("phòng trống")) {
                phongTrong.add(room);
            }
        }
        if(phongTrong.isEmpty()){
            System.out.println("Hiện tại Không có phòng trống");
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

            for(Room rooms : phongTrong){
                if(rooms.getID() == phongId){
                    room = rooms;
                }
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
        System.out.println("Nhập gi chú : ");
        dp.setGhiChu(sc.nextLine());
        dp.setId(getMaxId()+1);
        dp.setMaKhachHang(khachHangId);
        dp.setMaPhong(phongId);
        dp.setThoiGianDat(ngayNhan);
        dp.setThoiGianTra(ngayTra);
        dp.setTrangThai(TrangThai.CHUA_THANH_TOAN);

        listDatPhong.add(dp);
        for(Room rooms : listRoom){
            if(rooms.getID() == phongId){
                rooms.setTrangThai(TinhTrang.valueOf("DA_THUE"));
            }
        }

        System.out.println("✅ Đặt phòng thành công!");
    }

    public void traPhongVaThanhToanExel(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã dat phong : ");
        Long id = sc.nextLong();
        DatPhong datPhong = null;
        Room room = null;
        Customer customer = null;
        for(DatPhong dp : listDatPhong){
            if(dp.getId() == id){
                datPhong = dp;
            }
        }

        if (datPhong == null) {
            System.out.println("Không tìm thấy thông tin đặt phòng!");
            return;
        }

        for(Room rooms : listRoom){
            if(rooms.getID() == datPhong.getMaPhong()){
                room = rooms;
            }
        }
        if (room == null) {
            System.out.println("Không tìm thấy phòng!");
            return;
        }
        for (Customer customers : listCustomer){
            if(customers.getID() == datPhong.getMaKhachHang()){
                customer = customers;
            }
        }
        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng");
            return;
        }

        long soNgay = Duration.between(datPhong.getThoiGianDat(), datPhong.getThoiGianTra()).toDays();
        if (soNgay <= 0) soNgay = 1;

        Long giaTien = room.getGiaPhong() * soNgay;

        room.setTrangThai(TinhTrang.PHONG_TRONG);
        datPhong.setTrangThai(TrangThai.DA_THANH_TOAN);

        System.out.println("Trả phòng thành công!");
        System.out.println("Khách thuê: " + customer.getTen()+ " có cccd : "+customer.getCCCD());
        System.out.println("Phòng: " + room.getTenPhong());
        System.out.println("Số ngày thuê: " + soNgay);
        System.out.println("Thành tiền: " + giaTien + " VND");
    }
    public void hienThiTatCaHoaDonExel(){
        List<DatPhong> hoadons = new ArrayList<>();

        for(DatPhong dp : listDatPhong){
            hoadons.add(dp);
        }

        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
    public void hienThiTatCaHoaDonChuaThanhToanExel(){

        List<DatPhong> hoadons = new ArrayList<>();

        for(DatPhong dp : listDatPhong){
            if(dp.getTrangThai().getDescription().equalsIgnoreCase("chưa thanh toán")){
                hoadons.add(dp);
            }
        }

        if (hoadons.isEmpty()) {
            System.out.println("không có hóa đơn");
        }else {
            for (DatPhong dp : hoadons) {
                System.out.println(dp);
            }
        }
    }
    public void luuDanhSachDatPhong(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (DatPhong datPhong : listDatPhong) {
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
        String[] headers = {"ID Đặt Hàng","ID Khách Hàng", "ID Phòng", "Ngày nhận", "Ngày trả", "Gi chú"," Trạng Thái"};
        exportToExcel(data, headers,filePath);
        System.out.println("Danh sách đặt phòng đã được lưu vào file: " + filePath);
    }
    private  Long getMaxId() {
        if (listDatPhong == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (DatPhong datPhong : listDatPhong) {
            if (datPhong != null && datPhong.getId() > maxId) {
                maxId = datPhong.getId();
            }
        }
        return maxId;
    }
}
