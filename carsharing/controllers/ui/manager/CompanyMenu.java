package carsharing.controllers.ui.manager;

import carsharing.controllers.Input;
import carsharing.database.Database;
import carsharing.database.elements.Car;
import carsharing.database.elements.CarDAO;
import carsharing.database.elements.Company;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMenu {

    private static CarDAO dao;
    private static Company company;

    public static void display(Company company) {
        dao = new CarDAO(Database.getInstance());
        CompanyMenu.company = company;

        while (true) {
            System.out.println(company.getName() + " company:\n" +
                    "1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");

            String input = Input.getLine();

            switch (input.trim()) {
                case "1":
                    printCarList();
                    break;
                case "2":
                    createCar();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }


    private static void printCarList() {
        List<Car> list;

        try {
            list = dao.getAll()
                    .stream()
                    .filter(x -> x.getCompanyID() == company.getID())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (list.isEmpty()) {
            System.out.println("\nThe car list is empty!\n");
            return;
        }

        System.out.println("\nChoose the car:");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, list.get(i).getName());
        }

        System.out.println();
    }

    private static void createCar() {
        System.out.println("\nEnter the car name:");

        String name  = Input.getLine();

        try {
            dao.add(new Car(name, company.getID()));
            System.out.println("Car was created");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Car wasn't created\n");
        }
    }
}
