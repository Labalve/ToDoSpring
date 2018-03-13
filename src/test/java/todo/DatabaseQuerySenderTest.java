package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Labalve
 */
public class DatabaseQuerySenderTest {

    @Test
    public void testSelectTestTable() throws SQLException {
        createTestDatabase();
        createTestTable();
        insertIntoTestTable();
        String queryResult = getQueryDatabaseResult();
        dropTestDatabase();
        Assert.assertEquals(getExcpectedQueryResult(), queryResult);
    }

    private String getExcpectedQueryResult() {
        return "<record title = \"mock_title01\" description = \"mock_description01\"/><record title = \"mock_title02\" description = \"mock_description02\"/>";
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

    private void createTestTable() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("USE mock_database01;");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(getCreateTableCommand());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseConnection.close();
    }

    private void insertIntoTestTable() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement = databaseConnection.prepareStatement("USE mock_database01;");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(getInsertIntoTableCommand());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        databaseConnection.close();
    }

    private String getQueryDatabaseResult() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection databaseConnection = databaseConnector.getDatabaseConnection();
        PreparedStatement preparedStatement;
        preparedStatement = databaseConnection.prepareStatement("USE mock_database01;");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(getQueryCommand());
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultPrintable = "";
        while (resultSet.next()) {
            resultPrintable += "<record ";
            resultPrintable += "title = \"" + resultSet.getString("title");
            resultPrintable += "\" description = \"" + resultSet.getString("description");
            resultPrintable += "\"/>";
        }
        preparedStatement.close();
        databaseConnection.close();
        return resultPrintable;
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

    private String getCreateTableCommand() {
        String createCommand = "CREATE TABLE mock_table01 ( id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(30) NOT NULL,"
                + "description VARCHAR(30) NOT NULL);";
        return createCommand;
    }

    private String getInsertIntoTableCommand() {
        String createCommand = "INSERT INTO mock_table01 (title, description) "
                + "VALUES ('mock_title01', 'mock_description01'), ('mock_title02', 'mock_description02');";
        return createCommand;
    }

    private String getQueryCommand() {
        String queryCommand = "SELECT * FROM mock_table01;";
        return queryCommand;
    }

}
