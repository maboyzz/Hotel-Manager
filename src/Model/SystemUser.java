package Model;

import java.util.Date;

public class SystemUser extends Person{
    private String chucVu;
    public SystemUser(Long ID, String ten, String namSinh, String CCCD, String chucVu) {
        super(ID, ten, namSinh, CCCD);
        this.chucVu = chucVu;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}
