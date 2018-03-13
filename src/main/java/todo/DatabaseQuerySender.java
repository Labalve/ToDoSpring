package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseQuerySender {
    
    private final Connection databaseConnection;
    private final String query;
    private PreparedStatement preparedStatement;
    
    private DatabaseQuerySender(String query) throws SQLException {
        this.query = query;
        DatabaseConnector databaseConnector = new DatabaseConnector(); 
        databaseConnection = databaseConnector.getDatabaseConnection();
    }
    
    public static void executeQuery(String query) throws SQLException {
        DatabaseQuerySender databaseQuerySender = new DatabaseQuerySender(query);
        databaseQuerySender.handleQuery();
    }
    
    private void handleQuery() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        databaseConnection.close();
    }
}
