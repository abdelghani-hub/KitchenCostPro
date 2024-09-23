package main.java.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import main.resources.config.DBConnection;
import utils.*;

public class GenericDAOImpl<T> implements GenericDAO<T> {
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
            } finally {
                DBConnection.getInstance().closeConnection();
            }
        } catch (SQLException e) {
            ConsoleUI.printError(e.getMessage());
        }
        return entities;
    }

    @Override
    public Optional<T> save(T entity) {
        String query = "INSERT INTO " + tableName + " (";
        Field[] fields = getAllFields(entityClass);

        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                query += StringUtil.camelToSnake(field.getName()) + ", ";
            }
        }
        query = query.substring(0, query.length() - 2) + ") VALUES (";
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
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
                setPreparedStatementParameters(statement, entity, fields, false);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            } finally {
                DBConnection.getInstance().closeConnection();
            }
        } catch (SQLException e) {
            ConsoleUI.printError(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) {
        String query = "UPDATE " + tableName + " SET ";
        Field[] fields = getAllFields(entityClass);
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                query += StringUtil.camelToSnake(field.getName()) + " = ?, ";
            }
        }
        query = query.substring(0, query.length() - 2) + " WHERE id = ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return Optional.empty();
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setPreparedStatementParameters(statement, entity, fields, true);
                if (statement.executeUpdate() > 0) {
                    return Optional.of(entity);
                }
            } catch (IllegalAccessException e) {
                ConsoleUI.printError(e.getMessage());
            } finally {
                DBConnection.getInstance().closeConnection();
            }
        } catch (SQLException e) {
            ConsoleUI.printError(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> delete(T entity) {
        return Optional.empty();
    }

    public Optional<T> findByColumn(String column, Object value) {
        String query = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return Optional.empty();
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                switch (value.getClass().getSimpleName()) {
                    case "String":
                        statement.setString(1, (String) value);
                        break;
                    case "Integer":
                        statement.setInt(1, (Integer) value);
                        break;
                    case "Double":
                        statement.setDouble(1, (Double) value);
                        break;
                    case "Boolean":
                        statement.setBoolean(1, (Boolean) value);
                        break;
                    default:
                        ConsoleUI.printError("Unsupported data type");
                        return Optional.empty();
                }
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            } finally {
                DBConnection.getInstance().closeConnection();
            }
        } catch (SQLException e) {
            ConsoleUI.printError(e.getMessage());
            e.fillInStackTrace();
        }
        return Optional.empty();
    }

    public List<T> findAllByColumn(String column, Object value) {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                return entities;
            }
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                switch (value.getClass().getSimpleName()) {
                    case "String":
                        statement.setString(1, (String) value);
                        break;
                    case "Integer":
                        statement.setInt(1, (Integer) value);
                        break;
                    case "Double":
                        statement.setDouble(1, (Double) value);
                        break;
                    case "Boolean":
                        statement.setBoolean(1, (Boolean) value);
                        break;
                    default:
                        ConsoleUI.printError("Unsupported data type");
                        return entities;
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    T entity = mapResultSetToEntity(resultSet);
                    entities.add(entity);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                ConsoleUI.printError(e.getMessage());
            } finally {
                DBConnection.getInstance().closeConnection();
            }
        } catch (SQLException e) {
            ConsoleUI.printError(e.getMessage());
        }
        return entities;
    }

    private Field[] getAllFields(Class<?> entityClass) {
        List<Field> fields = new ArrayList<>();
        while (entityClass != null) {
            fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
            entityClass = entityClass.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private void setPreparedStatementParameters(PreparedStatement statement, T entity, Field[] fields, boolean isUpdate)
            throws SQLException, IllegalAccessException {
        int index = 1;
        for (Field field : fields) {
            if (!isUpdate && field.getName().equals("id")) {
                continue;
            }
            if (isUpdate && field.getName().equals("id")) {
                field.setAccessible(true);
                statement.setObject(fields.length, field.get(entity));
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(entity);
            if (value instanceof LocalDate) {
                statement.setDate(index++, java.sql.Date.valueOf((LocalDate) value));
            } else {
                statement.setObject(index++, value);
            }
        }
    }

    private T mapResultSetToEntity(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();

            Field[] fields = getAllFields(entityClass);
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = resultSet.getObject(StringUtil.camelToSnake(field.getName()));

                if (value != null) {
                    if (field.getType().equals(LocalDate.class)) {
                        field.set(entity, ((java.sql.Date) value).toLocalDate());
                    } else {
                        field.set(entity, value);
                    }
                }
            }
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error instantiating entity class", e);
        }
    }
}
