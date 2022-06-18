package carsharing.controllers;

import java.util.Scanner;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getLine() {
        return scanner.nextLine();
    }

    public static int getInt() {
        return Integer.parseInt(scanner.nextLine().trim());
    }

}
