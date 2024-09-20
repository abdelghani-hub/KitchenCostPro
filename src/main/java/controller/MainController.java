package main.java.controller;

import main.java.model.*;
import main.java.service.*;
import utils.ConsoleUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MainController {
    private final ClientService clientService;
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final LaborService laborService;
    private final QuoteService quoteService;

    public MainController() {
        this.clientService = new ClientService();
        this.projectService = new ProjectService();
        this.materialService = new MaterialService();
        this.laborService = new LaborService();
        this.quoteService = new QuoteService();
    }

    public void createNewProject() {
        Client client = selectOrCreateClient();

        if (client == null) {
            ConsoleUI.printError("Client selection/creation failed. Cannot proceed with project creation.");
            return;
        }

        ConsoleUI.printInfo("Proceeding with the selected client: " + client.getName());
        // Proceed with project creation
        Project project = projectService.create(client);

        ConsoleUI.printInfo("\nAdding Materials :");
        List<Material> materials = materialService.createMaterials();

        ConsoleUI.printInfo("\nAdding Labors :");
        List<Labor> labors = laborService.createLabors();

        ConsoleUI.printInfo("\nCalculating Project Cost :");

        project.setTVA(0.0);
        if (ConsoleUI.readBoolean("Do you want to apply a TVA for this project? (y/n) : ")) {
            project.setTVA(ConsoleUI.readDouble("Enter TVA (%) : "));
        }

        project.setProfitMargin(0.0);
        if (ConsoleUI.readBoolean("\nDo you want to apply a profit margin for this project? (y/n) : ")) {
            project.setProfitMargin(ConsoleUI.readDouble("Enter Profit Margin (%) : "));
        }

        ConsoleUI.printInfo("\nCalculating Project Total Cost ...");
        projectService.calculateTotalCost(project, client, materials, labors);

        Optional<Project> project_saved = projectService.saveProject(project, materials, labors);
        project_saved.ifPresentOrElse(
                p -> {
                    ConsoleUI.printSuccess(p.getName() + " project created successfully.");
                    generateQuote(p);
                },
                () -> ConsoleUI.printError("Project creation failed.")
        );
    }

    public void generateQuote(Project project) {
        Optional<Quote> quote = quoteService.createNewQuote(project);
        if (quote.isPresent()) {
            ConsoleUI.printSuccess("Quote generated successfully.");
            ConsoleUI.print(quote.get().toString());
        } else {
            ConsoleUI.printWarning("Quote Ignored.");
        }
    }

    public Client selectOrCreateClient() {
        ConsoleUI.printInfo("Client Searching ...");
        Map<String, String> clientChoices = new HashMap<>();
        clientChoices.put("1", "Create New Client");
        clientChoices.put("2", "Select Existing Client");

        String clientChoice = ConsoleUI.readChoice("Enter your choice: ", clientChoices);

        switch (clientChoice) {
            case "1":
                ConsoleUI.printInfo("Creating New Client ...");
                Optional<Client> createdClient = clientService.createNewClient();

                if (createdClient.isPresent()) {
                    ConsoleUI.printSuccess("Client Created Successfully.");
                    return createdClient.get();
                } else {
                    ConsoleUI.printError("Client Creation Failed.");
                    return null;
                }

            case "2":
                ConsoleUI.printInfo("Selecting Existing Client ...");
                String clientName = ConsoleUI.read("Enter Client Name: ", true);
                Optional<Client> selectedClient = clientService.findByName(clientName);

                if (selectedClient.isPresent()) {
                    ConsoleUI.printSuccess("Client Selected : ");
                    ConsoleUI.print(selectedClient.get().toString());
                    return selectedClient.get();
                } else {
                    ConsoleUI.printError("Client not found.");
                    return null;
                }

            default:
                ConsoleUI.printError("Invalid choice. Please try again.");
                return null;
        }
    }

    public void showExistingProjects() {
        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            ConsoleUI.printWarning("No projects found.");
        } else {
            ConsoleUI.printInfo("Existing Projects : ");
            projects.forEach(p -> ConsoleUI.print(p.toString()));
        }
    }
}
