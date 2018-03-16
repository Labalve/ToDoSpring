package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Labalve
 */
public class DatabaseToDoSelector {

    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String PROJECTS_TABLE_NAME = "projects";

    private final Connection databaseConnection;
    private String databaseName = "ToDo";
    private PreparedStatement preparedStatement;
    private String query;
    private String uuid;

    public DatabaseToDoSelector() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public DatabaseToDoSelector(String databaseName) throws SQLException {
        this.databaseName = databaseName;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public ResultSet getProject(String uuid) throws SQLException {
        this.uuid = uuid;
        String selectSQL = "SELECT * FROM " + PROJECTS_TABLE_NAME + " WHERE uuid ?";
        ResultSet projectResultSet = handleSingleSelect();
        databaseConnection.close();
        return projectResultSet;
    }
    
//    private void saveParentProject(Task taskBean) throws SQLException {
//        insertCommand = getProjectInsertCommand(taskBean.getProject());
//        handleInsert();
//    }

    private ResultSet handleSingleSelect() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setString(0, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        return resultSet;
    }

}
