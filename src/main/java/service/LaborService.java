package main.java.service;

import main.java.model.Labor;
import main.java.model.Project;
import main.java.repository.LaborRepository;
import utils.ConsoleUI;

import java.util.ArrayList;
import java.util.List;

public class LaborService {

    private final LaborRepository laborRepository;

    public LaborService() {
        this.laborRepository = new LaborRepository();
    }

    public void save(Labor labor) {
        laborRepository.save(labor);
    }

    public List<Labor> createLabors() {
        List<Labor> labors = new ArrayList<>();
        boolean addMore = true;
        while (addMore) {
            Labor labor = new Labor();
            labor.setName(ConsoleUI.read("\tEnter Labor Name : ", true));
            labor.setHourlyRate(ConsoleUI.readDouble("\tEnter Labor Rate (per hour) : "));
            labor.setHoursWorked(ConsoleUI.readDouble("\tEnter Labor Worked Hours : "));
//            labor.setTVARate(ConsoleUI.readDouble("\tEnter TVA Rate (%) : "));
            labor.setProductivity(ConsoleUI.readDouble("\tEnter Labor Productivity : "));
            labor.setComponentType("labor");
            labors.add(labor);
            ConsoleUI.printSuccess("Labor Added Successfully.");
            addMore = ConsoleUI.readBoolean("Do you want to add more labors? (y/n) : ");
        }
        return labors;
    }

    public List<Labor> findByProject(Project project) {
        return laborRepository.findAllByColumn("project_id", project.getId());
    }
}
