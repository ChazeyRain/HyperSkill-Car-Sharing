package carsharing.database.elements;

public class Company implements Comparable<Company> {

    private final int id;
    private final String name;

    public Company(String name) {
        this(-1, name);
    }

    Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    @Override
    public int compareTo(Company company) {
        return id < company.id ? -1 : id > company.id ? 1 : name.compareTo(company.name);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }
}
