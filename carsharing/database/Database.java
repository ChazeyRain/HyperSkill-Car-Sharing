package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database db;

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL;

    private Connection conn;

    private final String fileName;


    Database(String fileName) {
        this.fileName = fileName;
        DB_URL = "jdbc:h2:./src/carsharing/db/" + fileName;
        createNewConnection();
    }

    private void createNewConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void open(String fileName) {
        if (db != null) {
            db.closeConnection();
        }
        db = new Database(fileName);
    }

    public static Database getInstance() {
        return db;
    }

}
