package carsharing.database.elements;

import carsharing.database.DAO;
import carsharing.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO implements DAO<Company> {

    private final Database db;
    private final Connection connection;

    public CompanyDAO(Database db) {
        this.db = db;
        connection = db.getConnection();
    }

    @Override
    public List<Company> getAll() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM COMPANY");

        List<Company> companies = new ArrayList<>();

        while (rs.next()) {
            companies.add(new Company(rs.getInt("ID"), rs.getString("name")));
        }

        return companies;
    }

    @Override
    public void update(Company element, Company updatedElement) {

    }

    @Override
    public void add(Company element) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO COMPANY (name)" +
                "VALUES ('" + element.getName() + "');");
    }

    @Override
    public void remove(Company element) {

    }
}
