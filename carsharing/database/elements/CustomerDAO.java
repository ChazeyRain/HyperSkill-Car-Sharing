package carsharing.database.elements;

import carsharing.database.DAO;
import carsharing.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {

    private final Database db;
    private final Connection connection;


    public CustomerDAO(Database db) {
        this.db = db;
        this.connection = db.getConnection();
    }

    @Override
    public List<Customer> getAll() throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM CUSTOMER");

        ArrayList<Customer> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Customer(rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getObject("RENTED_CAR_ID", Integer.class)));
        }

        return list;
    }

    @Override
    public void update(Customer element, Customer updatedElement) throws SQLException {
        Statement statement = connection.createStatement();


        statement.execute(String.format("UPDATE CUSTOMER SET RENTED_CAR_ID = %d WHERE ID = %d;",
                updatedElement.getRentedCarID(), element.getId()));
    }

    @Override
    public void add(Customer element) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute(String.format("INSERT INTO CUSTOMER (NAME) VALUES ('%s');", element.getName()));
    }

    @Override
    public void remove(Customer element) throws SQLException {

    }
}
