package main.resources.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.ConsoleUI;

public class DBConnection {

    private final String dbname;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private Connection connection;
    private static DBConnection instance;

    private DBConnection() {
        this.dbname = "kitchencostpro";
        this.host = "127.0.0.1";
        this.port = "5432";
        this.username = "GreenPulse";
        this.password = "";
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + dbname, username, password);
            }
            return connection;
        } catch (SQLException e) {
            ConsoleUI.printError("Failed to establish a database connection: " + e.getMessage());
            return null;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ConsoleUI.printError("Failed to close the database connection: " + e.getMessage());
        }
    }
}
