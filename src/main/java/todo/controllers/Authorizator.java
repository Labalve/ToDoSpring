package todo.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import todo.DatabaseConnector;
import todo.User;
import todo.User.Role;

/**
 *
 * @author Labalve
 */
public class Authorizator {

    static Authorizator instance;

    private static final String USERS_TABLE_NAME = "users";
    
    Connection databaseConnection;

    private Authorizator() {
        try {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            databaseConnection = databaseConnector.getDatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Authorizator creation failed");
        }
    }

    private static Authorizator getInstance() {
        if (instance == null) {
            instance = new Authorizator();
        }
        return instance;
    }

    public static boolean isUser(String key) {
        Authorizator authorizator = getInstance();
        try {
            return authorizator.checkIfIsUser(key);
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean isAdmin(String key) {
        Authorizator authorizator = getInstance();
        try {
            return authorizator.checkIfIsAdmin(key);
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean checkIfIsUser(String key) throws SQLException {
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM users WHERE api_key = '?';");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt(1) > 0);
        } else {
            return false;
        }
    }

    private boolean checkIfIsAdmin(String key) throws SQLException {
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM users WHERE api_key = '?' AND role = '" + User.Role.ADMIN + "';");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt(1) > 0);
        } else {
            return false;
        }
    }
    
    private Role getKeyRole(String key) throws SQLException{
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("SELECT role FROM users WHERE api_key = '?';");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Role.valueOf(resultSet.getString("role"));
    }

}
