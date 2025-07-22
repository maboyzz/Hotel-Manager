package Model;

import java.util.Date;

public class Person {
    private Long ID;
    private String ten;
    private String namSinh;
    private String CCCD;

    public Person(Long ID, String ten, String namSinh, String CCCD) {
        this.ID = ID;
        this.ten = ten;
        this.namSinh = namSinh;
        this.CCCD = CCCD;
    }

    public Person() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", ten='" + ten + '\'' +
                ", namSinh='" + namSinh + '\'' +
                ", CCCD='" + CCCD + '\'' +
                '}';
    }
}
