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
    public static final String AQUA = "\u001B[94m";
    public static final String RED = "\u001B[31m";
    public static final String ORANGE = "\u001B[38;5;214m";
    public static final Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        System.out.print(
                "\n |=================================|" +
                        "\n |        " + ORANGE + "Kitchen COST PRO" + RESET + "         |" +
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
        System.out.println(RED + "\n" + message + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "\n" + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.print(ORANGE + "\n" + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(BLUE + "\n" + message + RESET);
    }

    public static void printPrimary(String message) {
        System.out.println(AQUA + "\n" + message + RESET);
    }

    public static void print(String message) {
        System.out.print(message);
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

    public static String read(String prompt, Boolean isRequired) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (isRequired && input.isEmpty()) {
            printError("This field is required.");
            return read(prompt, true);
        }
        return input;
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

    public static String readChoice(final String prompt, final Map<String, String> choices) {
        final String choicesSTR = buildChoicesString(choices, prompt);

        String choice;
        do {
            System.out.print(choicesSTR);
            choice = scanner.nextLine().trim();
            if (!choices.containsKey(choice)) {
                printError("Invalid choice! Please try again.");
            }
        } while (!choices.containsKey(choice));

        return choice;
    }

    private static String buildChoicesString(final Map<String, String> choices, final String prompt) {
        StringBuilder choicesSTR = new StringBuilder();
        choicesSTR.append("\t|------------------|\n");

        choices.forEach((key, value) ->
                choicesSTR.append(String.format("\t %s. %s\n", key, value)));

        choicesSTR.append("\t|__________________|\n")
                .append(prompt);

        return choicesSTR.toString();
    }

    public static String formatDouble(Double value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }

    public static Boolean readBoolean(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }

    public static LocalDate[] readPeriod(String startDatePrompt, String endDatePrompt) {
        LocalDate startDate;
        LocalDate endDate;
        while (true) {
            startDate = readLocalDate(startDatePrompt);
            endDate = readLocalDate(endDatePrompt);

            if (!startDate.isAfter(endDate)) {
                return new LocalDate[]{startDate, endDate};
            }
            printError("Invalid period. Please try again.");
        }
    }
}