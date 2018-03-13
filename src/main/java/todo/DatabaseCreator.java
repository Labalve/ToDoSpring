package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseCreator {

    private static String defaultDatabaseName = "MockToDo"; 
    static private DatabaseCreator instance;
    
    private String databaseName;
    private Connection databaseConnection;
    private PreparedStatement preparedStatement;
    
    public static void createDatabase() throws SQLException {
        DatabaseCreator databaseCreator = getInstance();
        databaseCreator.implCreateDatabase();
    }
    
    private void implCreateDatabase() throws SQLException {
        sendDropDatabaseCommand();
        sendCreateDatabaseCommand();
        preparedStatement.close();
        databaseConnection.close();
    }
    
    private void sendCreateDatabaseCommand() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement(getDatabaseCreatingQuery());
        preparedStatement.execute();
    }
    
    private void sendDropDatabaseCommand() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement(getDatabaseDroppingQuery());
        preparedStatement.execute();
    }
    
    private DatabaseCreator() {
        try {
            databaseName = defaultDatabaseName;
            DatabaseConnector databaseConnector = new DatabaseConnector();
            databaseConnection = databaseConnector.getDatabaseConnection();
        } catch (SQLException e) {
            System.out.println("Database Connection during creation of Database Creator: " + e.getMessage());
        }
    }

    private static DatabaseCreator getInstance() {
        if (instance == null) {
            instance = new DatabaseCreator();
        }
        return instance;
    }
    private String getDatabaseDroppingQuery() {
String query = "DROP DATABASE IF EXISTS " + databaseName + ";";
        return query;
    }
    private String getDatabaseCreatingQuery() {
        String query = "CREATE DATABASE " + databaseName + ";";
        return query;
    }

}
