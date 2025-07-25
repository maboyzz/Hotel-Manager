package Model;

public class Person {
    private Long ID;
    private String name;
    private String birthYear;
    private String citizenId;

    public Person(Long ID, String name, String birthYear, String citizenId) {
        this.ID = ID;
        this.name = name;
        this.birthYear = birthYear;
        this.citizenId = citizenId;
    }

    public Person() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", ten='" + name + '\'' +
                ", namSinh='" + birthYear + '\'' +
                ", CCCD='" + citizenId + '\'' +
                '}';
    }
}
