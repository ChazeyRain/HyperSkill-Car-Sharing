package carsharing.database.elements;

import carsharing.database.DAO;
import carsharing.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDAO implements DAO<Car> {

    private final Database db;
    private final Connection connection;

    public CarDAO(Database db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public List<Car> getAll() throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet set = statement.executeQuery("SELECT * FROM CAR");

        List<Car> cars = new ArrayList<>();

        while (set.next()) {
            cars.add(new Car(
                    set.getInt("ID"),
                    set.getString("NAME"),
                    set.getInt("COMPANY_ID")));
        }

        return cars;
    }

    @Override
    public void update(Car element, Car updatedElement) throws SQLException {

    }

    @Override
    public void add(Car element) throws SQLException {
        connection.prepareStatement(
                String.format("INSERT INTO CAR (NAME, COMPANY_ID) VALUES('%s', %d)",
                        element.getName(), element.getCompanyID()))
                .executeUpdate();
    }

    @Override
    public void remove(Car element) throws SQLException {

    }
}
