package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            insertCommand = getProperTaskCommand((Task) toDoBean);
        } else if (toDoBean instanceof Project) {
            insertCommand = getProperProjectCommand((Project) toDoBean);
        }
        handleInsert();
        databaseConnection.close();
    }

    private void saveParentProject(Task taskBean) throws SQLException {
        insertCommand = getProperProjectCommand(taskBean.getProject());
        handleInsert();
    }

    private void handleInsert() throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        System.out.println(preparedStatement.toString());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private String getProperTaskCommand(Task taskBean) throws SQLException {
        if (alreadyExists(taskBean)) {
            return getTaskUpdateCommand(taskBean);
        } else {
            return getTaskInsertCommand(taskBean);
        }
    }
    
    private String getProperProjectCommand(Project projectBean) throws SQLException {
        if (alreadyExists(projectBean)) {
            return getProjectUpdateCommand(projectBean);
        } else {
            return getProjectInsertCommand(projectBean);
        }
    }

    private boolean alreadyExists(ToDo toDo) throws SQLException {
        if (countOf(toDo) > 0) {
            return true;
        }
        return false;
    }

    private int countOf(ToDo toDo) throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        if (toDo instanceof Task) {
            preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM " + TASKS_TABLE_NAME + " WHERE UUID = ?;");
        } else {
            preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM " + PROJECTS_TABLE_NAME + " WHERE UUID = ?;");
        }
        preparedStatement.setString(1, toDo.getUuid());
        ResultSet resultSet = preparedStatement.executeQuery();
        int count;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        } else {
            count = 0;
        }
        preparedStatement.close();
        return count;
    }

    private String getTaskInsertCommand(Task taskBean) {
        String taskInsertCommand = "INSERT INTO " + TASKS_TABLE_NAME + " (uuid, title, description, project_id, outcome, date_due, author_id) ";
        taskInsertCommand += "VALUES ('" + taskBean.getUuid() + "','" + taskBean.getTitle() + "','" + taskBean.getDescription() + "'," + ((taskBean.getProjectUuid() == null) ? "NULL" : "'" + taskBean.getProjectUuid() + "'") + ",'" + taskBean.getOutcome() + "',";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(taskBean.getDateDue().getTime());
            taskInsertCommand += "'" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            taskInsertCommand += "NULL";
        }
        taskInsertCommand += taskBean.getAuthorUuid() == null ? ", NULL" : ", '" + taskBean.getAuthorUuid() + "'";
        taskInsertCommand += ");";  
        return taskInsertCommand;
    }

    private String getTaskUpdateCommand(Task taskBean) {
        String taskUpdateCommand = "UPDATE " + TASKS_TABLE_NAME + " SET title = \"" + taskBean.getTitle() + "\", description = \"";
        taskUpdateCommand += taskBean.getDescription() + "\", project_id = " + ((taskBean.getProjectUuid() == null) ? "NULL" : "'" + taskBean.getProjectUuid() + "'");
        taskUpdateCommand += ", outcome = \"" + taskBean.getOutcome() + "\", ";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(taskBean.getDateDue().getTime());
            taskUpdateCommand += "date_due = '" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            taskUpdateCommand += "NULL";
        }
        taskUpdateCommand += " WHERE uuid = '" + taskBean.getUuid() + "';";
        return taskUpdateCommand;
    }

    private String getProjectInsertCommand(Project projectBean) {
        String projectInsertCommand = "INSERT INTO " + PROJECTS_TABLE_NAME + " (uuid, title, description, outcome, date_due, author_id) ";
        projectInsertCommand += "VALUES ('" + projectBean.getUuid() + "','" + projectBean.getTitle() + "','" + projectBean.getDescription() + "','" + projectBean.getOutcome() + "',";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(projectBean.getDateDue().getTime());
            projectInsertCommand += "'" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            projectInsertCommand += "NULL";
        }
        projectInsertCommand += projectBean.getAuthorUuid() == null ? ", NULL" : ", '" + projectBean.getAuthorUuid() + "'";
        projectInsertCommand += ");";
        return projectInsertCommand;
    }
    
        private String getProjectUpdateCommand(Project projectBean) {
        String projectUpdateCommand = "UPDATE " + PROJECTS_TABLE_NAME + " SET title = \"" + projectBean.getTitle() + "\", description = \"";
        projectUpdateCommand += projectBean.getDescription() + "\", ";
        projectUpdateCommand += "outcome = \"" + projectBean.getOutcome() + "\"";
        try {
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(projectBean.getDateDue().getTime());
            projectUpdateCommand += ", date_due = '" + sqlTimestamp + "'";
        } catch (ToDoDateDueNullException e) {
            
        }
        projectUpdateCommand += " WHERE uuid = '" + projectBean.getUuid() + "';";
        return projectUpdateCommand;
    }


}
