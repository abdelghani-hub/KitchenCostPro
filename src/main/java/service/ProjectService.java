package main.java.service;

import main.java.enumerations.ProjectStatus;
import main.java.model.Client;
import main.java.model.Labor;
import main.java.model.Material;
import main.java.model.Project;
import main.java.repository.ProjectRepository;
import utils.ConsoleUI;

import java.util.List;
import java.util.Optional;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MaterialService materialService;
    private final LaborService laborService;

    public ProjectService() {
        this.projectRepository = new ProjectRepository();
        this.materialService = new MaterialService();
        this.laborService = new LaborService();
    }

    public Project create(Client client) {
        Project project = new Project();
        project.setClient_id(client.getId());
        project.setStatus(ProjectStatus.IN_PROGRESS.toString());

        String name;
        while (true){
            name = ConsoleUI.read("Enter Project Name : ", true);
            if(projectRepository.findByColumn("name", name).isEmpty())
                break;
            ConsoleUI.printError("A project with this name is already exists!");
        }
        Double area = ConsoleUI.readDouble("Enter Project Area (in m²) : ");
        project.setName(name);
        project.setArea(area);

        return project;
    }

    public Double calculateTotalCost(Project project, Client client, List<Material> materials, List<Labor> labors) {

        // Project and Client infos
        ConsoleUI.print(
                "\nProject Name : " + project.getName() +
                        "\nClient       : " + client.getName() +
                        "\nAddress      : " + project.getArea() +
                        "\nArea         : " + project.getArea() + " m²"
        );

        Double finalCost = showProjectCostDetails(project, materials, labors, client);
        return finalCost;
    }

    public Double showProjectCostDetails(Project project, List<Material> materials, List<Labor> labors, Client client) {
        ConsoleUI.printInfo("\nCost Details :");
        Double materialsCost = 0.0;
        Double laborsCost = 0.0;

        // Materials Details
        ConsoleUI.printPrimary("\t1) Materials");
        for (Material material : materials) {
            ConsoleUI.print(material.toString());
            materialsCost += material.calculateCost();
        }
        Double materialsTTC = Double.valueOf(materialsCost * (1 + project.getTVA() / 100));
        ConsoleUI.print(
                "\n\tTotal Materials Cost HT  : " + materialsCost + " €" +
                        "\n\tTotal Materials Cost TTC : " + ConsoleUI.ORANGE + materialsTTC + " €" + ConsoleUI.RESET
        );

        // Labors Details
        ConsoleUI.printPrimary("\n\t2) Labors");
        for (Labor labor : labors) {
            ConsoleUI.print(labor.toString());
            laborsCost += labor.calculateCost();
        }
        Double laborsTTC = Double.valueOf(laborsCost * (1 + project.getTVA() / 100));
        ConsoleUI.print(
                "\n\tTotal Labors Cost HT  : " + laborsCost + " €" +
                        "\n\tTotal Labors Cost TTC : " + ConsoleUI.ORANGE + laborsTTC + " €" + ConsoleUI.RESET
        );

        // Project Total Cost
        project.setTotalCost(materialsTTC + laborsTTC);

        ConsoleUI.printPrimary("\n\t3) Total Cost Before Profit Margin : " + project.getTotalCost() + " €");
        Double profit = project.getTotalCost() * (project.getProfitMargin() / 100);
        Double totalCost = project.getTotalCostWithMargin();
        ConsoleUI.printPrimary("\t4) Profit Margin (" + project.getProfitMargin() + "%)           : " + profit + " €");
        Double discount = client.getIsProfessional() ? (totalCost * client.getDiscountRate()/100) : 0.0;
        Double finalCost = totalCost - discount;
        ConsoleUI.printInfo("******************************");
        ConsoleUI.printInfo(" Total Cost      : " + ConsoleUI.formatDouble(totalCost) + " €");
        ConsoleUI.printInfo(" Client Discount : " + ConsoleUI.formatDouble(discount) + " €");
        ConsoleUI.printPrimary(" Final Cost      : " + ConsoleUI.YELLOW + ConsoleUI.formatDouble(finalCost) + " €");
        ConsoleUI.printInfo("******************************");
        return finalCost;
    }

    public Optional<Project> saveProject(Project project, List<Material> materials, List<Labor> labors) {
        Optional<Project> savedProject = projectRepository.save(project);
        if (savedProject.isPresent()) {
            Integer projectId = savedProject.get().getId();
            for (Material material : materials) {
                material.setProjectId(projectId);
                materialService.save(material);
            }
            for (Labor labor : labors) {
                labor.setProjectId(projectId);
                laborService.save(labor);
            }
            return savedProject;
        }
        return Optional.empty();
    }

    public Optional<Project> findByColumn(String name, Object s) {
        return projectRepository.findByColumn(name, s);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
