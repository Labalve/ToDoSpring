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
            if (null != ((Task) toDoBean).getProject()) {
                saveParentProject((Task) toDoBean);
            }
            insertCommand = getTaskInsertCommand((Task) toDoBean);
        } else if (toDoBean instanceof Project) {
            insertCommand = getProjectInsertCommand((Project) toDoBean);
        }
        handleInsert();
        databaseConnection.close();
    }

    private void saveParentProject(Task taskBean) throws SQLException {
        insertCommand = getProjectInsertCommand(taskBean.getProject());
        handleInsert();
    }

    private void handleInsert() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        preparedStatement.execute();
        preparedStatement.close();
    }

    private String getTaskInsertCommand(Task taskBean) {
        String insertCommand = "INSERT INTO " + TASKS_TABLE_NAME + " (uuid, title, description, project_id, outcome, date_due) ";
        insertCommand += "VALUES ('" + taskBean.getUuid() + "','" + taskBean.getTitle() + "','" + taskBean.getDescription() + "'," + ((taskBean.getProjectUuid() == null) ? "NULL" : "'" + taskBean.getProjectUuid() + "'") + ",'" + taskBean.getOutcome() + "',";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(taskBean.getDateDue().getTime());
            insertCommand += "'" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            insertCommand += "NULL";
        }
        insertCommand += ");";
        return insertCommand;
    }

    private String getProjectInsertCommand(Project projectBean) {
        String insertCommand = "INSERT INTO " + PROJECTS_TABLE_NAME + " (uuid, title, description, outcome, date_due) ";
        insertCommand += "VALUES ('" + projectBean.getUuid() + "','" + projectBean.getTitle() + "','" + projectBean.getDescription() + "','" + projectBean.getOutcome() + "',";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(projectBean.getDateDue().getTime());
            insertCommand += "'" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            insertCommand += "NULL";
        }
        insertCommand += ");";
        return insertCommand;
    }

}
