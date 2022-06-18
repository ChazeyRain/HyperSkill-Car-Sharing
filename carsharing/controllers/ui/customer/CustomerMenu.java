package carsharing.controllers.ui.customer;

import carsharing.controllers.Input;
import carsharing.database.Database;
import carsharing.database.elements.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu {

    private static Database db;
    private static Customer customer;
    private static CustomerDAO dao;
    private static CompanyDAO companyDAO;
    private static CarDAO carDAO;

    public static void display(Customer customer) {
        CustomerMenu.customer = customer;

        db = Database.getInstance();
        dao = new CustomerDAO(db);
        companyDAO = new CompanyDAO(db);
        carDAO = new CarDAO(db);

        while (true) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");

            String input = Input.getLine();

            switch (input) {
                case "1":
                    rentCar();
                    break;
                case "2":
                    returnCar();
                    break;
                case "3":
                    carInfo();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }

    private static void rentCar() {

        if (null != customer.getRentedCarID()) {
            System.out.println("You've already rented a car!");
            return;
        }

        companyChooseMenu();
    }

    private static void companyChooseMenu() {

        List<Company> list;

        try {
            list = companyDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (list.isEmpty()) {
            System.out.println("\nThe company list is empty!\n");
            return;
        }

        System.out.println("\nChoose the company:");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, list.get(i).getName());
        }

        System.out.println("0. Back");

        try {
            int option = Input.getInt() - 1;

            if (option == -1) {
                return;
            }

            carChooseMenu(list.get(option));
        } catch (Exception e) {
            System.out.println("Wrong input!\n");
        }
    }

    private static void carChooseMenu(Company company) {
        List<Car> list;
        List<Customer> customers;

        try {
            customers = dao.getAll().stream()
                    .filter(c -> c.getRentedCarID() != null)
                    .collect(Collectors.toList());

            list = carDAO.getAll().stream()
                    .filter(c -> c.getCompanyID() == company.getID()
                            && customers.stream().noneMatch(x -> x.getRentedCarID() == c.getID()))
                    .parallel()
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        if (list.isEmpty()) {
            System.out.printf("No available cars in the %s company%n", company.getName());
            return;
        }

        System.out.println("\nChoose a car:");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, list.get(i).getName());
        }

        System.out.println("0. Back");


        try {
            int option = Input.getInt() - 1;

            if (option == -1) {
                return;
            }

            //rent logic
            Customer updatedCustomer = new Customer(customer.getId(), customer.getName(), list.get(option).getID());

            dao.update(customer, updatedCustomer);

            customer = updatedCustomer;

            System.out.printf("\nYou rented '%s'\n\n", list.get(option).getName());

        } catch (Exception e) {
            System.out.println("Wrong input!\n");
        }
    }

    private static void returnCar() {
        if (null == customer.getRentedCarID()) {
            System.out.println("You didn't rent a car!");
            return;
        }

        try {
            Customer updatedCustomer = new Customer(customer.getId(), customer.getName(), null);
            dao.update(customer, updatedCustomer);
            customer = updatedCustomer;
            System.out.println("You've returned a rented car!");
        } catch (SQLException e) {
            System.out.println("Can not return this car!");
        }
    }

    private static void carInfo() {
        if (customer.getRentedCarID() == null) {
            System.out.println("You didn't rent a car!");
            return;
        }

        try {

            Car car = carDAO.getAll().stream()
                    .filter(c -> c.getID() == customer.getRentedCarID())
                    .findFirst().orElse(null);

            if (car == null) {
                System.out.println("Car does not exist if = " + customer.getRentedCarID());
                return;
            }

            Company company = companyDAO.getAll().stream()
                    .filter(c -> c.getID() == car.getCompanyID())
                    .findFirst().orElseThrow();

            System.out.println("Your rented car:");
            System.out.println(car.getName());
            System.out.println("Company:");
            System.out.println(company.getName());
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Something went wrong...");
        }

    }
}
