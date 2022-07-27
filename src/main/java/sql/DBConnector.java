package sql;

import util.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static final Properties POSTGRES_PROPERTIES = PropertiesManager.getProperty("postgres.properties");
    private static final String URL = POSTGRES_PROPERTIES.getProperty("url");
    private static final String USERNAME = POSTGRES_PROPERTIES.getProperty("username");
    private static final String PASSWORD = POSTGRES_PROPERTIES.getProperty("password");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        getConnection();
//        System.out.println("Connection to PostgreSQL is success");
//    }
}