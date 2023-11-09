package org.kainos.ea.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnector class provides methods
 * for establishing and managing database connections.
 */
public class DatabaseConnector {
    /**
     * instantiate a database connection.
     */
    private static Connection conn;

    /**
     * Gets a database connection. If a connection already exists
     * and is open, it is returned.
     * Otherwise, a new connection is established using environment variables.
     *
     * @return The database connection.
     * @throws SQLException If an error occurs while connecting to the database.
     */
    public Connection getConnection() throws SQLException {
        String user;
        String password;
        String host;
        String database;

        if (conn != null && !conn.isClosed()) {
            return conn;
        }

        try {
            user            = System.getenv("DB_USERNAME");
            password        = System.getenv("DB_PASSWORD");
            host            = System.getenv("DB_HOST");
            database        = System.getenv("DB_NAME");

            if (user == null || password == null || host == null) {
                throw new IllegalArgumentException(
                        "Environment variables not set.");
            }

            conn = DriverManager.getConnection(
                    "jdbc:mysql://"
                    + host + "/" + database
                            + "?allowPublicKeyRetrieval=true&useSSL=false",
                            user, password);

            return conn;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
