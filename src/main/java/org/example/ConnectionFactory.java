package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        ResourceBundle rb = ResourceBundle.getBundle("database");
        url = rb.getString("url");
        username = rb.getString("user");
        password = rb.getString("password");
    }

    public static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return con;
    }
}
