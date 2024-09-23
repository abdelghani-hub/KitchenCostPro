package main.java.service;

import main.java.model.Project;
import main.java.model.Quote;
import main.java.repository.QuoteRepository;
import utils.ConsoleUI;

import java.time.LocalDate;
import java.util.Optional;

public class QuoteService {

    private final QuoteRepository quoteRepository;

    public QuoteService() {
        this.quoteRepository = new QuoteRepository();
    }

    public Optional<Quote> createNewQuote(Project project, Double finalCost) {
        ConsoleUI.printInfo("Generating Quote ...");
        Quote quote = new Quote();
        quote.setProjectID(project.getId());
        quote.setEstimatedCost(finalCost);
        LocalDate[] dates = ConsoleUI.readPeriod("Enter Quote Issue Date dd/mm/YYYY : ", "Enter Quote Validity Date dd/mm/YYYY : ");
        quote.setIssueDate(dates[0]);
        quote.setValidityDate(dates[1]);
        if(ConsoleUI.readBoolean("Is the quote accepted by client? (y/n) : "))
            quote.acceptQuote();
        else
            quote.rejectQuote();
        if (ConsoleUI.readBoolean("Do you want to save the quote (y/n) : "))
            return quoteRepository.save(quote);
        return Optional.empty();
    }

    public Optional<Quote> findByProject(Project project) {
        return quoteRepository.findByColumn("project_id", project.getId());
    }

    public Optional<Quote> update(Quote quote) {
        return quoteRepository.update(quote);
    }
}
