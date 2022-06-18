package carsharing.controllers.ui;

import carsharing.controllers.Input;
import carsharing.controllers.ui.customer.CustomerMenu;
import carsharing.controllers.ui.manager.CompanyMenu;
import carsharing.controllers.ui.manager.ManagerMenu;
import carsharing.database.Database;
import carsharing.database.elements.Company;
import carsharing.database.elements.Customer;
import carsharing.database.elements.CustomerDAO;

import java.sql.SQLException;
import java.util.List;

public class MainMenu {

    private static CustomerDAO dao;
    public static void display() {

        dao = new CustomerDAO(Database.getInstance());

        while(true) {
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");

            String input = Input.getLine();

            switch (input.trim()) {
                case "1":
                    ManagerMenu.display();
                    break;
                case "2":
                    customerList();
                    break;
                case "3":
                    createCustomer();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private static void customerList() {

        List<Customer> list;

        try {
            list = dao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (list.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return;
        }

        System.out.println("\nCustomer list:");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, list.get(i).getName());
        }

        System.out.println("0. Back");

        try {
            int option = Input.getInt() - 1;

            if (option == -1) {
                return;
            }

            CustomerMenu.display(list.get(option));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong input!\n");
        }

    }

    private static void createCustomer() {
        System.out.println("\nEnter the customer name:");

        String name  = Input.getLine();

        try {
            dao.add(new Customer(name));
            System.out.println("The customer was added\n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("The customer wasn't added\n");
        }
    }
}
