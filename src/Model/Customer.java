package Model;

public class Customer extends Person{

    private int numberOfPeople;

    public Customer(Long ID, String ten, String namSinh, String CCCD, int numberOfPeople) {
        super(ID, ten, namSinh, CCCD);
        this.numberOfPeople = numberOfPeople;
    }
    public Customer() {
        super();
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-10s | %-15s | %-10s |",
                getID(), getName(), getBirthYear(), getCitizenId(), numberOfPeople);
    }
}
