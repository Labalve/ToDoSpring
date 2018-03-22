package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseUserInserter {

    private static final String USERS_TABLE_NAME = "users";

    private final Connection databaseConnection;
    private String databaseName = "ToDo";
    private PreparedStatement preparedStatement;
    private String insertCommand;

    public DatabaseUserInserter() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public DatabaseUserInserter(String databaseName) throws SQLException {
        this.databaseName = databaseName;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public void insertUser(User user) throws SQLException {
        insertCommand = getUserInsertCommand();
        handleInsert(user);
    }
    
    private void handleInsert(User user) throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        preparedStatement.setString(1, user.getKey());
        preparedStatement.setString(2, String.valueOf(user.getRole()));
        preparedStatement.execute();
        preparedStatement.close();
    }
    
    private String getUserInsertCommand() {
        String userInsertCommand = "INSERT INTO " + USERS_TABLE_NAME + " (api_key, role) ";
        userInsertCommand += "VALUES (?, ?);";
        return userInsertCommand;
    }

}
