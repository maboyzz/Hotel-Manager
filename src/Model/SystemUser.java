package Model;

public class SystemUser extends Person{
    private String role;
    public SystemUser(Long ID, String ten, String namSinh, String CCCD, String role) {
        super(ID, ten, namSinh, CCCD);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
