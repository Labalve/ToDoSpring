package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseInserter {
    private final String insertCommand;
    private final Connection databaseConnection;
    private PreparedStatement preparedStatement;
    
    private DatabaseInserter(String insertCommand) throws SQLException {
        this.insertCommand = insertCommand;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }
    
    public static void insertToDatabase(String insertCommand) throws SQLException {
        DatabaseInserter databaseInserter = new DatabaseInserter(insertCommand);
        databaseInserter.handleInsert();
    }
    
    private void handleInsert() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseConnection.close();
    }
    
}
