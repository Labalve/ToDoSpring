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
    private String userUuid;

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
            attachedTasks.add(resultSetToTask(resultSet));
        }
        return attachedTasks;
    }

    private Project resultSetToProject(ResultSet resultSet) throws SQLException {
        Project projectBean = new Project();
        projectBean.setUuid(resultSet.getString("uuid"));
        projectBean.setDateDue(resultSet.getDate("date_due"));
        projectBean.setTitle(resultSet.getString("title"));
        projectBean.setDescription(resultSet.getString("description"));
        projectBean.setOutcome(Outcome.valueOf(resultSet.getString("outcome")));
        projectBean.setAuthor(resultSet.getString("author_id"));
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

    private Task resultSetToTask(ResultSet resultSet) throws SQLException, InvalidToDoIdException {
        Task taskBean = new Task();
        taskBean.setUuid(resultSet.getString("uuid"));
        taskBean.setDateDue(resultSet.getDate("date_due"));
        taskBean.setTitle(resultSet.getString("title"));
        taskBean.setDescription(resultSet.getString("description"));
        taskBean.setOutcome(Outcome.valueOf(resultSet.getString("outcome")));
        taskBean.setAuthor(resultSet.getString("author_id"));
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
            Task taskBean = resultSetToTask(resultSet);
            return taskBean;
        } else {
            throw new InvalidToDoIdException(toDoClass, uuid);
        }
    }

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
            Project toDo = resultSetToProject(resultSet);
            preparedStatement.close();
            return toDo;
        }
    }

    String[] getAllTaskUuids(User user) throws SQLException {
        toDoClass = "Task";
        userUuid = user.getUuid();
        return getAllUuidsOfChosenType();
    }

    String[] getAllProjectUuids(User user) throws SQLException {
        toDoClass = "Project";
        userUuid = user.getUuid();
        return getAllUuidsOfChosenType();
    }
    
    String[] getAllUuidsOfChosenType() throws SQLException {
        String[] tasksUuids = new String[countOfType(toDoClass)];
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement("SELECT `uuid` FROM " + (toDoClass.equals("Task") ? TASKS_TABLE_NAME : PROJECTS_TABLE_NAME ) + " WHERE author_id = ?;");
        preparedStatement.setString(1, userUuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            tasksUuids[i++] = resultSet.getString("uuid");
        }
        return tasksUuids;
    }

    private int countOfType(String type) throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        if (type.equals("Task")) {
            preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM " + TASKS_TABLE_NAME + " WHERE author_id = ?;");
        } else {
            preparedStatement = databaseConnection.prepareStatement("SELECT COUNT(*) FROM " + PROJECTS_TABLE_NAME + " WHERE author_id = ?;");
        }
        preparedStatement.setString(1, userUuid);
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
}
