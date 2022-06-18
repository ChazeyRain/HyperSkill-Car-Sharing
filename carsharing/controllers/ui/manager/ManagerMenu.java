package carsharing.controllers.ui.manager;

import carsharing.controllers.Input;
import carsharing.database.Database;
import carsharing.database.elements.Company;
import carsharing.database.elements.CompanyDAO;

import java.sql.SQLException;
import java.util.List;

public class ManagerMenu {

    private static CompanyDAO companyDAO;
    public static void display() {
        companyDAO = new CompanyDAO(Database.getInstance());

        while (true) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");

            String input = Input.getLine();

            switch (input.trim()) {
                case "1":
                    printCompanyList();
                    break;
                case "2":
                    createCompany();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    private static void printCompanyList() {

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

            CompanyMenu.display(list.get(option));
        } catch (Exception e) {
            System.out.println("Wrong input!\n");
        }
    }

    private static void createCompany() {
        System.out.println("\nEnter the company name:");

        String name  = Input.getLine();

        try {
            companyDAO.add(new Company(name));
            System.out.println("Company was created");
        } catch (SQLException e) {
            System.out.println("Company wasn't created\n");
        }
    }
}
