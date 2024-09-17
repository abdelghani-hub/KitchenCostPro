package main.java;

import main.java.controller.MainController;
import utils.ConsoleUI;

public class Main
{
    private static final MainController controller = new MainController();
    public static void main(String[] args) {
        System.out.println(ConsoleUI.BLUE + "Welcome to the Cost Estimation System!" + ConsoleUI.RESET);
        while (true) {
            ConsoleUI.displayMenu();
            String choice = ConsoleUI.scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("--- Create New Project ---");
                    controller.createNewProject();
                    break;
                case "2":
                    System.out.println("--- Show Existing Projects ---");
                    break;
                case "3":
                    System.out.println("--- Calculate a Project Cost ---");
                    break;
                case "0":
                    System.out.println("--- Exiting... ---");
                    return;
                default:
                    ConsoleUI.printError("Invalid choice. Please try again.");
            }
        }
    }
}