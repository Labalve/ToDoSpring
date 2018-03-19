package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    private String toDoClass;

    public DatabaseToDoSelector() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public DatabaseToDoSelector(String databaseName) throws SQLException {
        this.databaseName = databaseName;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public Project getProject(String uuid) throws SQLException, InvalidToDoIdException {
        this.toDoClass = "Project";
        this.uuid = uuid;
        query = "SELECT * FROM " + PROJECTS_TABLE_NAME + " WHERE uuid = ?";
        Project project = (Project) handleSingleSelect();
        ArrayList<Task> attachedTasks = getAttachedTasks();
        project.attachTasks(attachedTasks);
        databaseConnection.close();
        return project;
    }

    private ArrayList<Task> getAttachedTasks() throws SQLException, InvalidToDoIdException {
        query = "SELECT * FROM " + TASKS_TABLE_NAME + " WHERE project_id = ?";
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Task> attachedTasks = new ArrayList<>();
        while (resultSet.next()) {
            attachedTasks.add(ResultSetToTask(resultSet));
        }
        return attachedTasks;
    }

    private Project ResultSetToProject(ResultSet resultSet) throws SQLException {
        Project projectBean = new Project();
        projectBean.setUuid(resultSet.getString("uuid"));
        projectBean.setDateDue(resultSet.getDate("date_due"));
        projectBean.setTitle(resultSet.getString("title"));
        projectBean.setDescription(resultSet.getString("description"));
        projectBean.setOutcome(Outcome.valueOf(resultSet.getString("outcome")));
        return projectBean;
    }

    public Task getTask(String uuid) throws SQLException, InvalidToDoIdException {
        this.toDoClass = "Task";
        this.uuid = uuid;
        query = "SELECT * FROM " + TASKS_TABLE_NAME + " WHERE uuid = ?";
        Task task = (Task) handleSingleSelect();
        databaseConnection.close();
        return task;
    }

    private Task ResultSetToTask(ResultSet resultSet) throws SQLException, InvalidToDoIdException {
        Task taskBean = new Task();
        taskBean.setUuid(resultSet.getString("uuid"));
        taskBean.setDateDue(resultSet.getDate("date_due"));
        taskBean.setTitle(resultSet.getString("title"));
        taskBean.setDescription(resultSet.getString("description"));
        taskBean.setOutcome(Outcome.valueOf(resultSet.getString("outcome")));
        try {
            String project_id = resultSet.getString("project_id");
            if (!resultSet.wasNull()) {
                taskBean.setProject(project_id);
            }
        } catch (WrongToDoTypeException e) {
            e.getMessage();
        }
        return taskBean;
    }

    private Task SingleResultSetToTask(ResultSet resultSet) throws SQLException, InvalidToDoIdException {
        if (resultSet.first()) {
            Task taskBean = ResultSetToTask(resultSet);
            return taskBean;
        } else {
            throw new InvalidToDoIdException(toDoClass, uuid);
        }
    }

//    private void saveParentProject(Task taskBean) throws SQLException {
//        insertCommand = getProjectInsertCommand(taskBean.getProject());
//        handleInsert();
//    }
    private ToDo handleSingleSelect() throws SQLException, InvalidToDoIdException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (toDoClass.equals("Task")) {
            Task toDo = SingleResultSetToTask(resultSet);
            preparedStatement.close();
            return toDo;
        } else {
            Project toDo = ResultSetToProject(resultSet);
            preparedStatement.close();
            return toDo;
        }
    }

}
