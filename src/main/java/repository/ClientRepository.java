package main.java.repository;

import main.java.dao.GenericDAO;
import main.java.dao.GenericDAOImpl;
import main.java.model.Client;

import java.util.List;
import java.util.Optional;

public class ClientRepository {
    private final GenericDAO<Client> dao;
    public ClientRepository(){
        this.dao = new GenericDAOImpl<>(Client.class, "client");
    }

    public Optional<Client> findById(String id) {
        return dao.findById(id);
    }

    public List<Client> findAll() {
        return dao.findAll();
    }

    public Optional<Client> save(Client entity) {
        return dao.save(entity);
    }

    public Optional<Client> update(Client entity) {
        return dao.update(entity);
    }

    public Optional<Client> delete(Client entity) {
        return dao.delete(entity);
    }

    public Optional<Client> findByColumn(String column, Object value) {
        return dao.findByColumn(column, value);
    }
}