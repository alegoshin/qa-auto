package sql.util;

import util.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {

    private static volatile Connection connection;

    private static final Properties POSTGRES_PROPERTIES = PropertiesManager.getProperty("postgres.properties");
    private static final String URL = POSTGRES_PROPERTIES.getProperty("url");
    private static final String USERNAME = POSTGRES_PROPERTIES.getProperty("username");
    private static final String PASSWORD = POSTGRES_PROPERTIES.getProperty("password");

    synchronized public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    synchronized public static void close() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}