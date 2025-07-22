package Model;

import java.time.LocalDateTime;
import java.util.Date;

public class DatPhong {
    private Long id;
    private Long maKhachHang;
    private Long maPhong;
    private LocalDateTime thoiGianDat;
    private LocalDateTime thoiGianTra;
    private String ghiChu;

    public DatPhong(Long id, Long maKhachHang, Long maPhong, LocalDateTime thoiGianDat, LocalDateTime thoiGianTra, String ghiChu) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
        this.thoiGianDat = thoiGianDat;
        this.thoiGianTra = thoiGianTra;
        this.ghiChu = ghiChu;
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


    @Override
    public String toString() {
        return "DatPhong{" +
                "id=" + id +
                ", khachHang=" + maKhachHang +
                ", phong=" + maPhong +
                ", thoiGianDat=" + thoiGianDat +
                ", thoiGianTra=" + thoiGianTra +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}