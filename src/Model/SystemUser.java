package Model;

import java.util.Date;

public class SystemUser extends Person{
    private String chucVu;
    public SystemUser(Long ID, String ten, Date ngaySinh, String CCCD, String chucVu) {
        super(ID, ten, ngaySinh, CCCD);
        this.chucVu = chucVu;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}
