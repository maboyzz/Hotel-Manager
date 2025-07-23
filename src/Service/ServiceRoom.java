package Service;

import DAO.RoomDAO;
import Model.Room;
import constant.LoaiPhong;
import constant.TinhTrang;
import constant.TrangThai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static DAO.ExportToExcel.exportToExcel;
import static Validator.RoomValidator.*;


public class ServiceRoom {

    ArrayList<Room> listRoom = new ArrayList<Room>();

    private boolean isInputBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private boolean checkInput() {
        if (listRoom == null || listRoom.isEmpty()) {
            System.out.println("Error: No matching data.");
            return false;
        }
        return true;
    }

    private Room inputRoomInfo() {
        Room room = new Room();
        Scanner sc = new Scanner(System.in);
        String tenPhong;
        LoaiPhong loaiPhong;
        String kichThuoc;
        TinhTrang trangThai;
        String tinhNang;


        do {
            System.out.println("Nhap ten phong : ");
            tenPhong = sc.nextLine();

            if (!isInputBlank(tenPhong) && isValidFullName(tenPhong)) {
                room.setTenPhong(tenPhong);
                break;
            }

        } while (true);
        do {
            System.out.println("Nhap loai phong (PHONG DON, PHONG DOI, PHONG VIP, PHONG TONG THONG): ");
            String input = sc.nextLine();
            boolean found = false;
            for (LoaiPhong lp : LoaiPhong.values()) {
                if (lp.getDescription().equalsIgnoreCase(input.trim())) {
                    loaiPhong = lp;
                    room.setLoaiPhong(loaiPhong);
                    found = true;
                    break;
                }
            }
            if (found) break;
            System.out.println("Loai phong khong hop le. Vui long nhap lai.");
        } while (true);
        do {
            System.out.println("Nhap kich thuoc phong : ");
            kichThuoc = sc.nextLine();

            if (!isInputBlank(kichThuoc)) {
                room.setKichThuoc(kichThuoc);
                break;
            }

        } while (true);
        do {
            System.out.println("Nhap trang thai phong (PHONG TRONG , CHO XAC NHAN, DA THUE, CHO DON DEP): ");
            String input = sc.nextLine();
            boolean found = false;
            for (TinhTrang tt : TinhTrang.values()) {
                if (tt.getDescription().equalsIgnoreCase(input.trim())) {
                    trangThai = tt;
                    room.setTrangThai(trangThai);
                    found = true;
                    break;
                }
            }
            if (found) break;
            System.out.println("Loai phong khong hop le. Vui long nhap lai.");
        } while (true);
        do {
            System.out.println("Nhap cac tinh nang cua phong : ");
            tinhNang = sc.nextLine();

            if (!isInputBlank(tinhNang) && isValidTinhNang(tinhNang)) {
                room.setTinhNang(tinhNang);
                break;
            }

        } while (true);
        return room;
    }

    private Long getMaxId() {


        if (listRoom == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (Room room : listRoom) {
            if (room != null && room.getID() > maxId) {
                maxId = room.getID();
            }
        }
        return maxId;

    }

    Long ID = 0L;

    public void addRoomsSQL() {
        System.out.println("Enter room information : ");
        Room room = inputRoomInfo();
        room.setGiaPhong(tinhgiaphong(room.getLoaiPhong()));
        new RoomDAO().insertRoom(room);
        System.out.println("Room added to database: " + room);
    }

    public void timPhongtrongSQL() {

        List<Room> rooms = new RoomDAO().TimPhongTrong();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    public void timToanBoPhongSQL() {


        List<Room> rooms = new RoomDAO().TimTatCaPhong();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    public void capNhatPhongSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã Phòng :");
        String idPhong = sc.nextLine();

        if (!isInputBlank(idPhong) && isValidIDPhong(idPhong)) {
            Room room = new RoomDAO().findRoomById(Long.parseLong(idPhong));
            if (room != null) {
                Room roomUpdate = inputRoomInfo();
                roomUpdate.setID(room.getID());
                roomUpdate.setGiaPhong(tinhgiaphong(roomUpdate.getLoaiPhong()));
                new RoomDAO().capNhatPhong(roomUpdate);
            } else {
                System.out.println("Không tìm thấy phòng !!!");
            }
        }


    }
    public void xoaPhongSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã Phòng :");
        String idPhong = sc.nextLine();

        if (!isInputBlank(idPhong) && isValidIDPhong(idPhong)) {
            Room room = new RoomDAO().findRoomById(Long.parseLong(idPhong));
            if (room != null) {
                new RoomDAO().xoaPhong(Long.parseLong(idPhong));
            } else {
                System.out.println("Không tìm thấy phòng !!!");
            }
        }
    }

    public void addRoomsExel() {
        System.out.println("Enter room information : ");
        Room room = inputRoomInfo();
        room.setID(getMaxId() + 1);
        room.setGiaPhong(tinhgiaphong(room.getLoaiPhong()));
        listRoom.add(room);
        System.out.println("Room added to" + room);
    }

    public void searchRoomsExel() {
        System.out.println("tất cả các phòng hiện có trong hệ thống : ");
        if (listRoom != null) {
            for (Room room : listRoom) {
                System.out.println("\n" + room.toString());
            }
        }
    }

    public void luuDanhSachPhong(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (Room room : listRoom) {
            data.add(new String[]{
                    String.valueOf(room.getID()),
                    room.getTenPhong(),
                    room.getLoaiPhong() != null ? room.getLoaiPhong().getDescription() : "",
                    room.getKichThuoc(),
                    room.getTrangThai() != null ? room.getTrangThai().getDescription() : "",
                    room.getTinhNang()
            });
        }
        // exportToExcel(data, filePath);
        System.out.println("Danh sách phòng đã được lưu vào file: " + filePath);
    }

    public void timKiemPhongTrong() {
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        for (Room room : listRoom) {
            if (room.getTrangThai().getDescription().equalsIgnoreCase("phòng trống")) {
                System.out.println("Phong trong: " + room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay phong trong");
        }
    }

    private long tinhgiaphong(LoaiPhong loaiPhong) {
        long gia = 0L;
        if (loaiPhong != null) {
            if (loaiPhong.getDescription().equalsIgnoreCase("phòng đơn")) {
                gia = 100000;
            } else if (loaiPhong.getDescription().equalsIgnoreCase("phòng đôi")) {
                gia = 200000;
            } else if (loaiPhong.getDescription().equalsIgnoreCase("phòng vip")) {
                gia = 300000;
            } else {
                gia = 400000;
            }
        }
        return gia;
    }
}
