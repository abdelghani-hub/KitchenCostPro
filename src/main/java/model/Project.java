package main.java.model;

import main.java.enumerations.ProjectStatus;

public class Project {
    private String name;
    private Double profitMargin;
    private Double totalCost;

    private ProjectStatus status;

    public Project(String name, Double profitMargin, Double totalCost, ProjectStatus status) {
        this.name = name;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public ProjectStatus getStatus() {
        return status;
    }
}
