package main.java.model;

import utils.ConsoleUI;

import java.time.LocalDate;

public class Quote {
    private Double estimatedCost;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private Boolean isAccepted;
    private Integer project_id;

    public Quote() {
    }

    public Quote(Double estimatedCost, LocalDate issueDate, LocalDate validityDate, Boolean isAccepted, Integer project_id) {
        this.estimatedCost = estimatedCost;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
        this.project_id = project_id;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public Integer getProject_id() {
        return project_id;
    }

    // Setters
    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public void setProjectID(Integer project_id) {
        this.project_id = project_id;
    }

    public String toString() {
        return
                "\n\testimatedCost : " + ConsoleUI.ORANGE + estimatedCost + " €" + ConsoleUI.RESET  +
                "\n\tissueDate     : " + issueDate +
                "\n\tvalidityDate  : " + validityDate +
                "\n\tisAccepted    : " + isAccepted ;
    }

    public void acceptQuote() {
        this.isAccepted = true;
    }

    public void rejectQuote() {
        this.isAccepted = false;
    }
}
