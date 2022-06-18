package carsharing.database.elements;

public class Customer {
    private final int id;
    private final String name;
    private final Integer rentedCarID;

    public Customer(String name) {
        this(-1, name, null);
    }

    public Customer(int id, String name, Integer rentedCarID) {
        this.id = id;
        this.name = name;
        this.rentedCarID = rentedCarID;
    }

    public Customer rentCar(Integer rentedCarID) {
        return new Customer(id, name, rentedCarID);
    }

    public Integer getRentedCarID() {
        return rentedCarID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
