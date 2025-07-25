package Model;

import constant.TrangThai;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatPhong {
    private Long id;
    private Long maKhachHang;
    private Long maPhong;
    private LocalDateTime thoiGianDat;
    private LocalDateTime thoiGianTra;
    private String ghiChu;
    private TrangThai trangThai;

    public DatPhong(Long id, Long maKhachHang, Long maPhong, LocalDateTime thoiGianDat, LocalDateTime thoiGianTra, String ghiChu, TrangThai trangThai) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
        this.thoiGianDat = thoiGianDat;
        this.thoiGianTra = thoiGianTra;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public DatPhong() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Long maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Long getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Long maPhong) {
        this.maPhong = maPhong;
    }

    public LocalDateTime getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(LocalDateTime thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public LocalDateTime getThoiGianTra() {
        return thoiGianTra;
    }

    public void setThoiGianTra(LocalDateTime thoiGianTra) {
        this.thoiGianTra = thoiGianTra;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public TrangThai getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThai trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        int width = 30; // chiều dài tối đa mỗi dòng ghi chú
        List<String> ghiChuLines = splitText(ghiChu, width);

        StringBuilder sb = new StringBuilder();

        // Dòng đầu tiên in đủ
        sb.append(String.format("| %-4s | %-10s | %-8s | %-19s | %-19s | %-30s | %-10s |",
                id, maKhachHang, maPhong, thoiGianDat, thoiGianTra, ghiChuLines.get(0), trangThai));

        // Các dòng tiếp theo in tiếp phần ghi chú
        for (int i = 1; i < ghiChuLines.size(); i++) {
            sb.append(String.format("\n| %-4s | %-10s | %-8s | %-19s | %-19s | %-30s | %-10s |",
                    "", "", "", "", "", ghiChuLines.get(i), ""));
        }

        return sb.toString();
    }
    private List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        while (text.length() > maxLength) {
            int splitPos = text.lastIndexOf(" ", maxLength);
            if (splitPos == -1) splitPos = maxLength;
            result.add(text.substring(0, splitPos).trim());
            text = text.substring(splitPos).trim();
        }
        result.add(text);
        return result;
    }
}