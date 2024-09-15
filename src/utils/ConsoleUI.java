package utils;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[92m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String ORANGE = "\u001B[38;5;214m";
    public static final Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        System.out.print(
                "\n |=================================|" +
                        "\n |  " + ORANGE + "Kitchen COST PRO" + RESET + "  |" +
                        "\n |=================================|" +
                        "\n | 1. Create New Project           |" +
                        "\n | 2. Show Existing Projects       |" +
                        "\n | 3. Calculate a Project Cost     |" +
                        "\n |" + BLUE + " 0. Exit" + RESET + "                         |" +
                        "\n |_________________________________|" +
                        "\n  Enter your choice : "
        );
    }

    public static void printError(String message) {
        System.out.println(RED + "\nError: " + message + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "\nSuccess: " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.print(ORANGE + "\nWarning: " + message + RESET);
    }

    // Read Local Date
    public static LocalDate readLocalDate(String prompt) {
        System.out.print(prompt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            String input = scanner.nextLine();
            try {
                date = LocalDate.parse(input, formatter);
                valid = true;
            } catch (DateTimeParseException e) {
                ConsoleUI.printError("Invalid format. Please try again ( dd/mm/YYYY ) : ");
            }
        }
        return date;
    }

    // Method for handling the Input Mismatch Exception
    public static int readInt(String prompt) {
        int result = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                validInput = true;
            } else {
                printError("Invalid input. Please enter a valid integer.");
            }
            scanner.nextLine();
        }

        return result;
    }
    // Method for handling the Input Mismatch Exception
    public static Double readDouble(String prompt) {
        double result = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                result = scanner.nextDouble();
                validInput = true;
            } else {
                printError("Invalid input. Please enter a valid Double.");
            }
            scanner.nextLine();
        }
        return result;
    }

    public static String readChoice(String prompt, Map<String, String> choices) {
        StringBuilder choicesSTR = new StringBuilder();
        choicesSTR.append("\t|------------------|\n");
        choices.forEach((key, value) -> choicesSTR.append("\t ")
                .append(key)
                .append(". ")
                .append(value)
                .append("\n"));
        choicesSTR.append("\t|__________________|\n").append(prompt);
        while (true) {
            System.out.print(choicesSTR);
            String choice = scanner.nextLine().trim();
            if (!choices.containsKey(choice))
                printError("invalid choice!");
            else
                return choice;
        }
    }

    public static String formatDouble(Double value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
}
