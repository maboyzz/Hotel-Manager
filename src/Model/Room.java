package Model;

import constant.LoaiPhong;
import constant.TinhTrang;

public class Room {
    private Long ID;
    private String tenPhong;
    private LoaiPhong loaiPhong;
    private String kichThuoc;
    private TinhTrang trangThai;
    private String tinhNang;

    public Room(Long ID, String tenPhong, LoaiPhong loaiPhong, String kichThuoc, TinhTrang trangThai, String tinhNang) {
        this.ID = ID;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.kichThuoc = kichThuoc;
        this.trangThai = trangThai;
        this.tinhNang = tinhNang;
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

    @Override
    public String toString() {
        return "Room{" +
                "ID=" + ID +
                ", tenPhong='" + tenPhong + '\'' +
                ", loaiPhong=" + loaiPhong +
                ", kichThuoc='" + kichThuoc + '\'' +
                ", trangThai=" + trangThai +
                ", tinhNang='" + tinhNang + '\'' +
                '}';
    }
}
