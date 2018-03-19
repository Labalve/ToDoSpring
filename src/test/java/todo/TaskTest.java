package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import static org.junit.Assert.fail;

/**
 *
 * @author Labalve
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TaskTest {

    @Test
    public void testTaskDateSetting() throws WrongToDoTypeException, ToDoDateDueNullException {
        Task taskBean = new Task();
        taskBean.setDateDue(setDateForTheTest());
        Assert.assertEquals(setDateForTheTest().getTime(), taskBean.getDateDue().getTime());
    }

    private Date setDateForTheTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Test(expected = ToDoDateDueNullException.class)
    public void testToDoDateDueNullException() throws ToDoDateDueNullException, WrongToDoTypeException {
        Task taskBean = new Task();
        taskBean.getDateDue();
    }

    @Test
    public void testTaskSave() throws WrongToDoTypeException, SQLException {
        dropTestDatabase();
        createTestDatabase();
        createTestTables();
        Task taskBean = (Task) ToDoTestingFactory.getBean("Task", "test_task01");
        try {
            taskBean.testSave();
        } catch (SQLException e) {
            fail("Saving bean failed with message: " + e.getMessage());
        }
        dropTestDatabase();
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
