package main.java.controller;

import main.java.model.*;
import main.java.service.*;
import utils.ConsoleUI;

import java.time.LocalDate;
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
        Double finalCost = projectService.calculateTotalCost(project, client, materials, labors);
        Optional<Project> project_saved = projectService.saveProject(project, materials, labors);

        project_saved.ifPresentOrElse(
                p -> {
                    ConsoleUI.printSuccess(p.getName() + " project created successfully.");
                    generateQuote(p, finalCost);
                },
                () -> ConsoleUI.printError("Project creation failed.")
        );
    }

    public void generateQuote(Project project, Double finalCost) {
        Optional<Quote> quote = quoteService.createNewQuote(project, finalCost);
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

    public void showProject() {
        String name = ConsoleUI.read("Enter Project Name : ", true);
        Optional<Project> optional = projectService.findByColumn("name", name);
        if (optional.isEmpty()) {
            ConsoleUI.printError("Project not found!");
            return;
        }
        Project project = optional.get();
        Optional<Client> optionalClient = clientService.findByColumn("id", project.getClient_id());
        if (optionalClient.isEmpty()) {
            ConsoleUI.printError("Client not found!");
            return;
        }
        Client client = optionalClient.get();
        List<Material> materials = materialService.findByProject(project);

        List<Labor> labors = laborService.findByProject(project);
        projectService.showProjectCostDetails(project, materials, labors, client);

        Optional<Quote> optionalQuote = quoteService.findByProject(project);
        if (optionalQuote.isEmpty())
            return;
        Quote quote = optionalQuote.get();

        ConsoleUI.printInfo("Project :");
        ConsoleUI.print(project.toString());

        ConsoleUI.printInfo("Quote details :");
        ConsoleUI.print(quote.toString());

        if (!quote.getIsAccepted()) {
            Boolean accepted = ConsoleUI.readBoolean(ConsoleUI.AQUA + "\nDo you want to accept the quote (y/n): " + ConsoleUI.RESET);
            if (accepted && quote.getValidityDate().isBefore(LocalDate.now())) {
                ConsoleUI.printWarning("The validity date is passed !");
            } else if (accepted) {
                quote.acceptQuote();
                if (quoteService.update(quote).isPresent())
                    ConsoleUI.printSuccess("Quote accepted successfully !");
                else
                    ConsoleUI.printError("Quote acceptance failed !");
            }
        } else
            ConsoleUI.printInfo("The quote is already accepted !");
    }
}
