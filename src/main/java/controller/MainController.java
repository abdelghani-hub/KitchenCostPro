package main.java.controller;

import main.java.model.*;
import main.java.service.ClientService;
import main.java.service.LaborService;
import main.java.service.MaterialService;
import main.java.service.ProjectService;
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

    public MainController() {
        this.clientService = new ClientService();
        this.projectService = new ProjectService();
        this.materialService = new MaterialService();
        this.laborService = new LaborService();
    }

    public void createNewProject() {
        Client client = selectOrCreateClient();

        if (client != null) {
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
                    },
                    () -> ConsoleUI.printError("Project creation failed.")
            );
        } else {
            ConsoleUI.printError("Client selection/creation failed. Cannot proceed with project creation.");
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


}
