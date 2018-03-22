package todo.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import todo.DatabaseConnector;
import todo.DatabaseUserSelector;
import todo.ToDo;
import todo.User;
import todo.User.Role;

/**
 *
 * @author Labalve
 */
public class Authorizator {

    static Authorizator instance;

    private static final String USERS_TABLE_NAME = "users";

    private static final String DATABASE_NAME = "ToDo";

    Connection databaseConnection;

    static boolean checkIsAuthor(String key, ToDo toDo) throws SQLException {
        DatabaseUserSelector userSelector = new DatabaseUserSelector();
        User user = userSelector.selectUserByKey(key);
        return user.getUuid().equals(toDo.getAuthorUuid());
    }

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
            System.out.println("key-checking error: " + e.getMessage());
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
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("USE " + DATABASE_NAME + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM users WHERE api_key = ?;");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt(1) > 0);
        } else {
            return false;
        }
    }

    private boolean checkIfIsAdmin(String key) throws SQLException {
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("USE " + DATABASE_NAME + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM users WHERE api_key = ? AND role = '" + User.Role.ADMIN + "';");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return (resultSet.getInt(1) > 0);
        } else {
            return false;
        }
    }

    private Role getKeyRole(String key) throws SQLException {
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("SELECT role FROM users WHERE api_key = '?';");
        preparedStatement.setString(1, key);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Role.valueOf(resultSet.getString("role"));
    }

}
