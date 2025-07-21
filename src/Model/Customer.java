package Model;

import java.util.Date;

public class Customer extends Person{

    private int soNguoi;

    public Customer(Long ID, String ten, Date ngaySinh, String CCCD, int soNguoi) {
        super(ID, ten, ngaySinh, CCCD);
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
}
