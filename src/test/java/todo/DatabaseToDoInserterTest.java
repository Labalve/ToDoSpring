package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Labalve
 */
public class DatabaseToDoInserterTest {

    @Before
    public void before() throws SQLException {
        dropTestDatabase();
        createTestDatabase();
        createTestTables();
    }
    
    @After
    public void after() throws SQLException {
        dropTestDatabase();
    }

    @Test
    public void testTaskInsertion() throws WrongToDoTypeException, SQLException {
        Task taskBean = (Task) ToDoTestingFactory.getContextBean("Task", "test_task01");
        DatabaseToDoInserter databaseInserter = new DatabaseToDoInserter("mock_database01");
        try {
            databaseInserter.saveToDo(taskBean);
        } catch (SQLException e) {
            fail("saving failed with message: " + e.getMessage());
        }
    }

    @Test
    public void testProjectInsertion() throws SQLException, WrongToDoTypeException {
        Project projectBean = (Project) ToDoTestingFactory.getContextBean("Project", "test_project01");
        DatabaseToDoInserter databaseInserter = new DatabaseToDoInserter("mock_database01");
        try {
            databaseInserter.saveToDo(projectBean);
        } catch (SQLException e) {
            fail("saving failed with message: " + e.getMessage());
        }
    }

    @Test
    public void testTaskWithProjectInsertion() throws WrongToDoTypeException, SQLException {
        Task taskBean = (Task) ToDoTestingFactory.getContextBean("Task", "test_task01");
        Project projectBean = (Project) ToDoTestingFactory.getContextBean("Project", "test_project01");
        taskBean.setProject(projectBean);
        DatabaseToDoInserter databaseInserter = new DatabaseToDoInserter("mock_database01");
        try {
            databaseInserter.saveToDo(taskBean);
        } catch (SQLException e) {
            fail("saving failed with message: " + e.getMessage());
        }
    }

    private void createTestDatabase() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement;
        preparedStatement = databaseConnection.prepareStatement(getDropDatabaseCommand());
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(getCreateDatabaseCommand());
        preparedStatement.execute();
        preparedStatement.close();
        databaseConnection.close();
    }

    private void createTestTables() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("USE mock_database01;");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(getCreateProjectTableCommand());
        preparedStatement.executeUpdate();
        preparedStatement = databaseConnection.prepareStatement(getCreateTaskTableCommand());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseConnection.close();
    }

    private void dropTestDatabase() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement;
        preparedStatement = databaseConnection.prepareStatement(getDropDatabaseCommand());
        preparedStatement.execute();
        preparedStatement.close();
        databaseConnection.close();
    }

    private String getDropDatabaseCommand() {
        String dropCommand = "DROP DATABASE IF EXISTS mock_database01;";
        return dropCommand;
    }

    private String getCreateDatabaseCommand() {
        String createCommand = "CREATE DATABASE mock_database01;";
        return createCommand;
    }

    private String getCreateTaskTableCommand() {
        String createCommand = "CREATE TABLE tasks ( uuid VARCHAR(50) PRIMARY KEY,"
                + "title VARCHAR(30),"
                + "description VARCHAR(120),"
                + "project_id VARCHAR(50),"
                + "date_due DATETIME,"
                + "outcome VARCHAR(30),"
                + "FOREIGN KEY (project_id) REFERENCES projects(uuid));";
        return createCommand;
    }

    private String getCreateProjectTableCommand() {
        String createCommand = "CREATE TABLE projects ( uuid VARCHAR(50) PRIMARY KEY,"
                + "title VARCHAR(30),"
                + "description VARCHAR(120),"
                + "date_due DATETIME,"
                + "outcome VARCHAR(30));";
        return createCommand;
    }
}
