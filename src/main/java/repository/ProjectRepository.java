package main.java.repository;

import main.java.dao.GenericDAO;
import main.java.dao.GenericDAOImpl;
import main.java.model.Project;

import java.util.List;
import java.util.Optional;

public class ProjectRepository {
    private final GenericDAO<Project> dao;

    public ProjectRepository(){
        this.dao = new GenericDAOImpl<>(Project.class, "project");
    }

    public Optional<Project> findById(String id) {
        return dao.findById(id);
    }

    public List<Project> findAll() {
        return dao.findAll();
    }

    public Optional<Project> save(Project entity) {
        return dao.save(entity);
    }

    public Optional<Project> update(Project entity) {
        return dao.update(entity);
    }

    public Optional<Project> delete(Project entity) {
        return dao.delete(entity);
    }

    public Optional<Project> findByColumn(String column, String value) {
        return dao.findByColumn(column, value);
    }

    public Optional<Project> findByColumnName(String name, String s) {
        return dao.findByColumn(name, s);
    }
}
