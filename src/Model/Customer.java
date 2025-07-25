package Model;

import java.util.Date;

public class Customer extends Person{

    private int soNguoi;

    public Customer(Long ID, String ten, String namSinh, String CCCD, int soNguoi) {
        super(ID, ten, namSinh, CCCD);
        this.soNguoi = soNguoi;
    }
    public Customer() {
        super();
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    @Override
    public String toString() {
        return "Customer{" +"ID= "+getID()+'\'' +
                ", Họ tên='" + getTen() + '\'' +
                ", Năm sinh='" + getNamSinh() + '\'' +
                ", CCCD='" + getCCCD() + '\'' +
                ", Số người=" + soNguoi +
                '}';
    }
}
