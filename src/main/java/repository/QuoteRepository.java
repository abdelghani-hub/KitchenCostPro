package main.java.repository;

import main.java.dao.GenericDAO;
import main.java.dao.GenericDAOImpl;
import main.java.model.Quote;

import java.util.List;
import java.util.Optional;

public class QuoteRepository {
    private final GenericDAO<Quote> dao;

    public QuoteRepository(){
        this.dao = new GenericDAOImpl<>(Quote.class, "quote");
    }

    public Optional<Quote> findById(String id) {
        return dao.findById(id);
    }

    public List<Quote> findAll() {
        return dao.findAll();
    }

    public Optional<Quote> save(Quote entity) {
        return dao.save(entity);
    }

    public Optional<Quote> update(Quote entity) {
        return dao.update(entity);
    }

    public Optional<Quote> delete(Quote entity) {
        return dao.delete(entity);
    }

    public Optional<Quote> findByColumn(String column, String value) {
        return dao.findByColumn(column, value);
    }
}
