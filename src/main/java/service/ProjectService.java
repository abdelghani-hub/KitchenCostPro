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

        java.lang.String name = ConsoleUI.read("Enter Project Name : ", true);
        Double area = ConsoleUI.readDouble("Enter Project Area (in m²) : ");
        project.setName(name);
        project.setArea(area);

        return project;
    }

    public void calculateTotalCost(Project project, Client client, List<Material> materials, List<Labor> labors) {
        Double materialsCost = 0.0;
        Double laborsCost = 0.0;
        // Project and Client infos
        ConsoleUI.print(
                "\nProject Name : " + project.getName() +
                        "\nClient       : " + client.getName() +
                        "\nAddress      : " + project.getArea() +
                        "\nArea         : " + project.getArea() + " m²"
        );

        ConsoleUI.printInfo("\nCost Details :");

        // Materials Details
        ConsoleUI.printPrimary("\t1) Materials");
        for (Material material : materials) {
            ConsoleUI.print(material.toString());
            materialsCost += material.calculateCost();
        }
        ConsoleUI.print(
                "\n\tTotal Materials Cost HT  : " + materialsCost + " €" +
                        "\n\tTotal Materials Cost TTC : " + ConsoleUI.ORANGE + materialsCost * (1 + project.getTVA() / 100) + " €" + ConsoleUI.RESET
        );

        // Labors Details
        ConsoleUI.printPrimary("\t2) Labors");
        for (Labor labor : labors) {
            ConsoleUI.print(labor.toString());
            laborsCost += labor.calculateCost();
        }
        ConsoleUI.print(
                "\n\tTotal Labors Cost HT  : " + laborsCost + " €" +
                        "\n\tTotal Labors Cost TTC : " + ConsoleUI.ORANGE + laborsCost * (1 + project.getTVA() / 100) + " €" + ConsoleUI.RESET
        );
        project.setTotalCost(materialsCost * (1 + project.getTVA() / 100) + laborsCost * (1 + project.getTVA() / 100));
        // Project Total Cost
        ConsoleUI.printPrimary("\t3) Total Cost Before Profit Margin : " + project.getTotalCost() + " €");
        double profit = project.getTotalCost() * (project.getProfitMargin() / 100);
        Double projectFinalCost = project.getTotalCostWithMargin();
        ConsoleUI.printPrimary("\t4) Profit Margin (" + project.getProfitMargin() + "%)           : " + profit + " €");
        ConsoleUI.printPrimary("***************************");
        ConsoleUI.printPrimary(" Final Cost : " + projectFinalCost + " €");
        ConsoleUI.printPrimary("***************************");
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

    public Optional<Project> findByColumn(String name, String s) {
        return projectRepository.findByColumn(name, s);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
