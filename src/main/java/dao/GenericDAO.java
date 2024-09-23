package main.java.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    Optional<T> findById(String id);
    List<T> findAll();
    Optional<T> save(T entity);
    Optional<T> update(T entity);
    Optional<T> delete(T entity);
    Optional<T> findByColumn(String column, Object value);
    List<T> findAllByColumn(String projectId, Object value);
}