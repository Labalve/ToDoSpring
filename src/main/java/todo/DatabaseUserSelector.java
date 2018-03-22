package todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import todo.User.Role;

/**
 *
 * @author Labalve
 */
public class DatabaseUserSelector {

    private static final String USERS_TABLE_NAME = "users";

    private final Connection databaseConnection;
    private String databaseName = "ToDo";
    private PreparedStatement preparedStatement;
    private String insertCommand;

    public DatabaseUserSelector() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public DatabaseUserSelector(String databaseName) throws SQLException {
        this.databaseName = databaseName;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnection = databaseConnector.getDatabaseConnection();
    }

    public User selectUser(String uuid) throws SQLException {
        insertCommand = getUserSelectByUuidCommand();
        return handleSelect(uuid);
    }

    public User selectUserByKey(String key) throws SQLException {
        insertCommand = getUserSelectByKeyCommand();
        return handleSelect(key);
    }

    private User handleSelect(String uuid) throws SQLException {
        preparedStatement = databaseConnection.prepareStatement("USE " + databaseName + ";");
        preparedStatement.execute();
        preparedStatement = databaseConnection.prepareStatement(insertCommand);
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = resultSetToUser(resultSet);
            preparedStatement.close();
            return user;
        } else {
            throw new IllegalArgumentException("Wrong user uuid provided");
        }
    }

    User resultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUuid(resultSet.getString("uuid"));
        user.setKey(resultSet.getString("api_key"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        return user;
    }
    
    private String getUserSelectByUuidCommand() {
        String userInsertCommand = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE uuid = ?;";
        return userInsertCommand;
    }
    
    private String getUserSelectByKeyCommand() {
        String userInsertCommand = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE api_key = ?;";
        return userInsertCommand;
    }

}
