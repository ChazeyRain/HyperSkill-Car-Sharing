package carsharing;


import carsharing.controllers.Controller;

public class Main {

    public static void main(String[] args) {
        // write your code here

        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {
                case "-databaseFileName":
                    Config.dataBaseFileName = args[i + 1];
                    break;
                default:
                    break;
            }
        }

        Controller.start();
    }
}