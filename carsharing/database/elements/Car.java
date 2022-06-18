package carsharing.database.elements;

public class Car {

    private final int id;
    private final String name;
    private final int companyID;

    public Car(String name, int companyID) {
        this(-1, name, companyID);
    }

    Car(int id, String name, int companyID) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyID() {
        return companyID;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
