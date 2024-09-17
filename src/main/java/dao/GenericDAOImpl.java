package main.java.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.resources.config.DBConnection;
import utils.*;

public class GenericDAOImpl<T>  implements  GenericDAO<T> {
    private final String tableName;
    private final Class<T> entityClass;

    public GenericDAOImpl(Class<T> entityClass, String tableName) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findById(String id) {
        return findByColumn("id", id);
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return entities;
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    T entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            }finally {
                DBConnection.getInstance().closeConnection();
            }
        }catch(SQLException e){
            ConsoleUI.printError(e.getMessage());
        }
        return entities;
    }

    @Override
    public Optional<T> save(T entity) {
        String query = "INSERT INTO " + tableName + " (";
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                query += StringUtil.camelToSnake(field.getName()) + ", ";
            }
        }
        query = query.substring(0, query.length() - 2) + ") VALUES (";
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equals("id")) {
                query += "?, ";
            }
        }
        query = query.substring(0, query.length() - 2) + ") RETURNING *";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return Optional.empty();
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setPreparedStatementParameters(statement, entity, fields);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            }finally {
                DBConnection.getInstance().closeConnection();
            }
        }catch(SQLException e){
            ConsoleUI.printError(e.getMessage());
        }
        return Optional.empty();
    }
    @Override
    public Optional<T> update(T entity) {
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(T entity) {
        return Optional.empty();
    }

    public Optional<T> findByColumn(String column, String value) {
        String query = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return Optional.empty();
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, value);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            }finally {
                DBConnection.getInstance().closeConnection();
            }
        }catch(SQLException e){
            ConsoleUI.printError(e.getMessage());
        }
        return Optional.empty();
    }

    private void setPreparedStatementParameters(PreparedStatement statement, T entity, Field[] fields)
            throws SQLException, IllegalAccessException {
        int index = 1;
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                field.setAccessible(true);
                statement.setObject(index++, field.get(entity));
            }
        }
    }

    private T mapResultSetToEntity(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        try {
            // Use getDeclaredConstructor() to get the no-argument constructor
            T entity = entityClass.getDeclaredConstructor().newInstance();

            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Allows access to private fields
                Object value = resultSet.getObject(StringUtil.camelToSnake(field.getName()));

                // Only set the field if the value is not null
                if (value != null) {
                    field.set(entity, value);
                }
            }
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error instantiating entity class", e);
        }
    }
}
