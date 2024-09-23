package main.java.repository;

import main.java.dao.GenericDAO;
import main.java.dao.GenericDAOImpl;
import main.java.model.Material;

import java.util.List;
import java.util.Optional;

public class MaterialRepository {

    private final GenericDAO<Material> dao;
    public MaterialRepository() {
        this.dao = new GenericDAOImpl<>(Material.class, "material");
    }

    public Optional<Material> save(Material material) {
        return dao.save(material);
    }

    public List<Material> findAllByColumn(String projectId, Object value) {
        return dao.findAllByColumn(projectId, value);
    }
}
