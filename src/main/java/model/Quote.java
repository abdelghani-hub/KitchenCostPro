package main.java.model;

import java.time.LocalDate;

public class Quote {
    private Double estimatedCost;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private Boolean isAccepted;

    public Quote(Double estimatedCost, LocalDate issueDate, LocalDate validityDate, Boolean isAccepted) {
        this.estimatedCost = estimatedCost;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
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

    public void generateQuote() {
        // Generate a quote
    }

    public void acceptQuote() {
        this.isAccepted = true;
    }

    public void rejectQuote() {
        this.isAccepted = false;
    }
}
