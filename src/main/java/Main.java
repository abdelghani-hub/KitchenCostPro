package main.java;

import main.java.controller.MainController;
import utils.ConsoleUI;

public class Main {
    private static final MainController controller = new MainController();

    public static void main(String[] args) {
        ConsoleUI.printInfo("Welcome to the Cost Estimation System!");
        while (true) {
            ConsoleUI.displayMenu();
            String choice = ConsoleUI.scanner.nextLine();
            switch (choice) {
                case "1":
                    ConsoleUI.printInfo("--- Create New Project ---");
                    controller.createNewProject();
                    break;
                case "2":
                    ConsoleUI.printInfo("--- Show Existing Projects ---");
                    controller.showExistingProjects();
                    break;
                case "3":
                    ConsoleUI.printInfo("--- Calculate a Project Cost ---");
                    controller.showProject();
                    break;
                case "0":
                    ConsoleUI.printInfo("Exiting...");
                    return;
                default:
                    ConsoleUI.printError("Invalid choice. Please try again.");
            }
        }
    }
}