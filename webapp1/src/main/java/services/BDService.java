package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDService {
    protected static final String URL = "jdbc:mysql://localhost:3306/database";
    protected static final String NAME = "root";
    protected static final String PASSWORD = "admin";
    protected static Connection connection;
    protected static PreparedStatement preparedStatement;

    public BDService(){}
    protected static void open(){
        try {
            connection = DriverManager.getConnection(URL,NAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
