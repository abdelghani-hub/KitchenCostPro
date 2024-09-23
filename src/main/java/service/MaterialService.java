package main.java.service;

import main.java.model.Material;
import main.java.model.Project;
import main.java.repository.MaterialRepository;
import utils.ConsoleUI;

import java.util.ArrayList;
import java.util.List;

public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService() {
        this.materialRepository = new MaterialRepository();
    }

    public void save(Material material) {
        materialRepository.save(material);
    }

    public List<Material> createMaterials() {
        List<Material> materials = new ArrayList<>();
        boolean addMore = true;
        while (addMore) {
            Material material = new Material();
            material.setName(ConsoleUI.read("\tEnter Material Name : ", true));
            material.setQuantity(ConsoleUI.readInt("\tEnter Material Quantity (m²) : "));
            material.setUnitCost(ConsoleUI.readDouble("\tEnter Unit Cost (€/m²) : "));
            material.setTransportCost(ConsoleUI.readDouble("\tEnter Transport Cost (€) : "));
            material.setQualityCoefficient(ConsoleUI.readDouble("\tEnter Quality ( 1:STD | > 1.0 : high) : "));
            material.setComponentType("material");
            materials.add(material);
            ConsoleUI.printSuccess("Material Added Successfully.");
            addMore = ConsoleUI.readBoolean("Do you want to add more materials? (y/n) : ");
        }
        return materials;
    }

    public List<Material> findByProject(Project project) {
        return materialRepository.findAllByColumn("project_id", project.getId());
    }
}
