package Model;

import constant.LoaiPhong;
import constant.TinhTrang;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Long ID;
    private String tenPhong;
    private LoaiPhong loaiPhong;
    private String kichThuoc;
    private TinhTrang trangThai;
    private String tinhNang;
    private long giaPhong;

    public Room(Long ID, String tenPhong, String kichThuoc, LoaiPhong loaiPhong, TinhTrang trangThai, String tinhNang, long giaPhong) {
        this.ID = ID;
        this.tenPhong = tenPhong;
        this.kichThuoc = kichThuoc;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
        this.tinhNang = tinhNang;
        this.giaPhong = giaPhong;
    }

    public Room() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public TinhTrang getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TinhTrang trangThai) {
        this.trangThai = trangThai;
    }

    public String getTinhNang() {
        return tinhNang;
    }

    public void setTinhNang(String tinhNang) {
        this.tinhNang = tinhNang;
    }

    public long getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(long giaPhong) {
        this.giaPhong = giaPhong;
    }


    @Override
    public String toString() {
        int width = 60; // độ dài tối đa cho mỗi dòng của "Tính năng"
        List<String> lines = splitText(tinhNang, width);

        StringBuilder sb = new StringBuilder();

        // Dòng đầu tiên: in đầy đủ
        sb.append(String.format("| %-2d | %-6s | %-18s | %-10s | %-13s | %-60s | %-9.2f |",
                ID, tenPhong, loaiPhong, kichThuoc, trangThai, lines.get(0), (double) giaPhong));

        // Các dòng tiếp theo: chỉ in phần tính năng
        for (int i = 1; i < lines.size(); i++) {
            sb.append(String.format("\n| %-2s | %-6s | %-18s | %-10s | %-13s | %-60s | %-9s |",
                    "", "", "", "", "", lines.get(i), ""));
        }

        return sb.toString();
    }
    private List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        while (text.length() > maxLength) {
            int splitPos = text.lastIndexOf(", ", maxLength);
            if (splitPos == -1) splitPos = maxLength;
            result.add(text.substring(0, splitPos).trim());
            text = text.substring(splitPos).replaceFirst("^,\\s*", "").trim();
        }
        result.add(text);
        return result;
    }
}
