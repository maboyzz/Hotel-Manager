package Service;

import DAO.RoomDAO;

import Model.Room;
import constant.RoomType;
import constant.RoomStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static DAO.ExportToExcel.exportToExcel;
import static Validator.RoomValidator.*;


public class RoomService {


    static ArrayList<Room> roomList = new ArrayList<Room>();

    private boolean isInputBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private boolean checkInput() {
        if (roomList == null || roomList.isEmpty()) {
            System.out.println("Error: No matching data.");
            return false;
        }
        return true;
    }

    private Room inputRoomInfo() {
        Room room = new Room();
        Scanner sc = new Scanner(System.in);
        String tenPhong;
        RoomType roomType;
        String kichThuoc;
        RoomStatus trangThai;
        String tinhNang;


        do {
            System.out.println("Nhap ten phong : ");
            tenPhong = sc.nextLine();

            if (!isInputBlank(tenPhong) && isValidFullName(tenPhong)) {
                room.setRoomName(tenPhong);
                break;
            }

        } while (true);
        do {
            System.out.println("Nhap loai phong (PHONG DON, PHONG DOI, PHONG VIP, PHONG TONG THONG): ");
            String input = sc.nextLine();
            boolean found = false;
            for (RoomType lp : RoomType.values()) {
                if (lp.getDescription().equalsIgnoreCase(input.trim())) {
                    roomType = lp;
                    room.setRoomType(roomType);
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
                room.setRoomSize(kichThuoc);
                break;
            }

        } while (true);
        do {
            System.out.println("Nhap trang thai phong (PHONG TRONG , CHO XAC NHAN, DA THUE, CHO DON DEP): ");
            String input = sc.nextLine();
            boolean found = false;
            for (RoomStatus tt : RoomStatus.values()) {
                if (tt.getDescription().equalsIgnoreCase(input.trim())) {
                    trangThai = tt;
                    room.setStatus(trangThai);
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
                room.setFeatures(tinhNang);
                break;
            }

        } while (true);
        return room;
    }

    private Long getMaxId() {
        if (roomList == null) {
            return 0L;
        }
        Long maxId = 0L;
        for (Room room : roomList) {
            if (room != null && room.getId() > maxId) {
                maxId = room.getId();
            }
        }
        return maxId;
    }

    Long ID = 0L;

    public void addRoomSQL() {
        System.out.println("Enter room information : ");
        Room room = inputRoomInfo();
        room.setPrice(calculateRoomPrice(room.getRoomType()));
        new RoomDAO().insertRoom(room);
        System.out.println("Room added to database: " + room);
    }

    public void findAvailableRoomsSQL() {

        List<Room> rooms = new RoomDAO().findAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    public void findAllRoomsSQL() {

        printHeader();
        List<Room> rooms = new RoomDAO().findAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
            System.out.println("+----+--------+---------------------+------------+---------------+-------------------------------------------------------------+-----------+");
        }
    }

    public void updateRoomSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã Phòng :");
        String idPhong = sc.nextLine();

        if (!isInputBlank(idPhong) && isValidIDPhong(idPhong)) {
            Room room = new RoomDAO().findRoomById(Long.parseLong(idPhong));
            if (room != null) {
                Room roomUpdate = inputRoomInfo();
                roomUpdate.setId(room.getId());
                roomUpdate.setPrice(calculateRoomPrice(roomUpdate.getRoomType()));
                new RoomDAO().updateRoom(roomUpdate);
            } else {
                System.out.println("Không tìm thấy phòng !!!");
            }
        }


    }

    public void deleteRoomSQL() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã Phòng :");
        String idPhong = sc.nextLine();

        if (!isInputBlank(idPhong) && isValidIDPhong(idPhong)) {
            Room room = new RoomDAO().findRoomById(Long.parseLong(idPhong));
            if (room != null) {
                new RoomDAO().deleteRoom(Long.parseLong(idPhong));
            } else {
                System.out.println("Không tìm thấy phòng !!!");
            }
        }
    }

    public void addRoomExcel() {
        System.out.println("Enter room information : ");
        Room room = inputRoomInfo();
        room.setId(getMaxId() + 1);
        room.setPrice(calculateRoomPrice(room.getRoomType()));
        roomList.add(room);
        System.out.println("Room added to" + room);
    }

    public void findAllRoomsExcel() {
         if (roomList != null) {
             printHeader();
             for (Room room : roomList) {
                System.out.println(room);

            }
             System.out.println("+----+--------+---------------------+------------+---------------+-------------------------------------------------------------+-----------+");

        }
    }

    public void saveRoomListToExcel(String filePath) {
        List<String[]> data = new ArrayList<>();
        // Add room data to the list
        for (Room room : roomList) {
            data.add(new String[]{
                    String.valueOf(room.getId()),
                    room.getRoomName(),
                    String.valueOf(room.getRoomType()),
                    room.getRoomSize(),
                    String.valueOf(room.getStatus()),
                    room.getFeatures(),
                    String.valueOf(room.getPrice())
            });
        }
        String[] headers = {"ID Phòng", "Tên Phòng", "Loại Phòng", "Kich Thước", "Trạng Thái", "Chức Năng", "Giá Tiền"};
        exportToExcel(data, headers, filePath);
        System.out.println("Danh sách phòng đã được lưu vào file: " + filePath);
    }

    public void findAvailableRoomsExcel() {
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        printHeader();
        for (Room room : roomList) {
            if (room.getStatus().getDescription().equalsIgnoreCase("phòng trống")) {
                System.out.println(room);
                found = true;
            }
        }
        System.out.println("+----+--------+---------------------+------------+---------------+-------------------------------------------------------------+-----------+");

        if (!found) {
            System.out.println("Khong tim thay phong trong");
        }
    }

    public Room findRoomByIdExcel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập Id phòng :");
        long idPhong = sc.nextLong();
        for (Room room : roomList) {
            if (room.getId() == idPhong) {
                return room;
            }
        }
        return null;
    }

    public void deleteRoomByIdExcel() {
        Room room = findRoomByIdExcel();
        if (room != null) {
            if (room.getStatus().equals(RoomStatus.OCCUPIED)) {
                System.out.println("phòng đang thuê không thể xóa");
                return;
            }
            roomList.remove(room);
            System.out.println("Xóa phòng thành công");
        }
    }

    public void updateRoomByIdExcel() {
        Room room = findRoomByIdExcel();
        if (room != null) {
            if (room.getStatus().equals(RoomStatus.OCCUPIED)) {
                System.out.println("Phòng đang được thuê, không thể cập nhật.");
                return;
            }
            Room roomUpdate = inputRoomInfo();
            roomUpdate.setId(room.getId());
            roomUpdate.setStatus(room.getStatus());

            int index = roomList.indexOf(room);
            if (index != -1) {
                roomList.set(index, roomUpdate);
                System.out.println("Cập nhật phòng thành công.");
            } else {
                System.out.println("Lỗi: Cập nhật phòng thất bại");
            }
        }
    }

    private long calculateRoomPrice(RoomType roomType) {
        long gia = 0L;
        if (roomType != null) {
            if (roomType.getDescription().equalsIgnoreCase("phòng đơn")) {
                gia = 100000;
            } else if (roomType.getDescription().equalsIgnoreCase("phòng đôi")) {
                gia = 200000;
            } else if (roomType.getDescription().equalsIgnoreCase("phòng vip")) {
                gia = 300000;
            } else {
                gia = 400000;
            }
        }
        return gia;
    }

    public void loadRoomListFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Sheet đầu tiên
            roomList.clear(); // Xóa dữ liệu cũ trước khi load

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua dòng tiêu đề (i = 1)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Room room = new Room();
                room.setId(getLongValue(row.getCell(0)));
                room.setRoomName(row.getCell(1).getStringCellValue());
                String loaiPhongStr = row.getCell(2).getStringCellValue();
                room.setRoomType(RoomType.valueOf(loaiPhongStr));
                String trangThaiStr = row.getCell(4).getStringCellValue();
                room.setStatus(RoomStatus.valueOf(trangThaiStr));
                room.setRoomSize(row.getCell(3).getStringCellValue());
                room.setFeatures(row.getCell(5).getStringCellValue());
                room.setPrice(getLongValue(row.getCell(6)));

                roomList.add(room);
            }
            System.out.println("P Đã import danh sách đặt phòng từ file Excel.");
        } catch (IOException e) {
            System.out.println("P Lỗi đọc file Excel: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("P Lỗi định dạng dữ liệu trong file Excel: " + e.getMessage());
        }
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

    public static void printHeader() {
        System.out.println("+----+--------+--------------------+------------+---------------+--------------------------------------------------------------+-----------+");
        System.out.printf("| %-2s | %-6s | %-18s | %-10s | %-13s | %-60s | %-9s |\n",
                "ID", "Mã", "Loại", "Kích thước", "Trạng thái", "Tính năng", "Giá");
        System.out.println("+----+--------+--------------------+------------+---------------+--------------------------------------------------------------+-----------+");
    }
}
