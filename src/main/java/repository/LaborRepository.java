package main.java.repository;

import main.java.dao.GenericDAOImpl;
import main.java.model.Labor;

import java.util.List;
import java.util.Optional;

public class LaborRepository {

    private final GenericDAOImpl<Labor> dao;

    public LaborRepository() {
        this.dao = new GenericDAOImpl<>(Labor.class, "labor");
    }

    public Optional<Labor> save(Labor labor) {
        return dao.save(labor);
    }

    public List<Labor> findAllByColumn(String projectId, Object value) {
        return dao.findAllByColumn(projectId, value);
    }
}
