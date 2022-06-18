package carsharing.controllers;

import carsharing.Config;
import carsharing.database.Database;
import carsharing.controllers.ui.MainMenu;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controller {
    public static void start() {
        Database.open(Config.dataBaseFileName);

        createCompanyTable();
        createCarTable();
        createCustomerTable();

        MainMenu.display();

        Database.getInstance().closeConnection();
    }

    private static void createCompanyTable() {
        try(PreparedStatement statement = Database.getInstance()
                .getConnection()
                .prepareStatement("CREATE TABLE COMPANY (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME VARCHAR UNIQUE NOT NULL);")) {
                    statement.executeUpdate();
        } catch (SQLException e) {
            //
        }
    }

    private static void createCarTable() {
        try(PreparedStatement statement = Database.getInstance()
                .getConnection()
                .prepareStatement("CREATE TABLE CAR (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR UNIQUE NOT NULL," +
                        "COMPANY_ID INT NOT NULL," +
                        "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            //
        }
    }

    private static void createCustomerTable() {
        try(PreparedStatement statement = Database.getInstance()
                .getConnection()
                .prepareStatement("CREATE TABLE CUSTOMER (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR UNIQUE NOT NULL," +
                        "RENTED_CAR_ID INT DEFAULT NULL," +
                        "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            //
        }
    }
}
