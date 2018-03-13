package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseToDoInserter {

    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String PROJECTS_TABLE_NAME = "projects";

    private final Connection databaseConnection;
    private String databaseName = "ToDo";
    private PreparedStatement preparedStatement;
    private String insertCommand;

    public DatabaseToDoInserter() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public DatabaseToDoInserter(String databaseName) throws SQLException {
        this.databaseName = databaseName;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public void saveToDo(ToDo toDoBean) throws SQLException {
        if (toDoBean instanceof Task) {
            insertCommand = getTaskInsertCommand((Task) toDoBean);
        } else if (toDoBean instanceof Project) {
            insertCommand = getProjectInsertCommand((Project) toDoBean);
        }
        handleInsert();
    }

    private void handleInsert() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseConnection.close();
    }

    private String getTaskInsertCommand(Task taskBean) {
        String insertCommand = "INSERT INTO " + TASKS_TABLE_NAME + " (id, title, description, project_id, outcome, date_due) ";
        insertCommand += "VALUES (1, '" + taskBean.getTitle() + "','" + taskBean.getDescription() + "'," + ((taskBean.getProject() == null) ? "NULL" : (String) taskBean.getProject()) + ", '" + taskBean.getOutcome() + "',";
        try {
            insertCommand += "'" + taskBean.getDateDue() + "'";
        } catch (ToDoDateDueNullException e) {
            insertCommand += "NULL";
        }
        insertCommand += ");";
        return insertCommand;
    }

    private String getProjectInsertCommand(Project projectBean) {
        String insertCommand = "INSERT INTO " + PROJECTS_TABLE_NAME + " (title, description, outcome, date_due) ";
        insertCommand += "VALUES ('" + projectBean.getTitle() + "','" + projectBean.getDescription() + "','" + projectBean.getOutcome() + "',";
        try {
            insertCommand += "'" + projectBean.getDateDue() + "'";
        } catch (ToDoDateDueNullException e) {
            insertCommand += "NULL";
        }
        insertCommand += ");";
        return insertCommand;
    }

}
