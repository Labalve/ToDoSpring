package todo;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class DatabaseConnector {
    
    static private String defaultDatabaseUserId = "mock_database_user01";
    
    private DatabaseUser databaseUser;
    private Database database;
    private Properties connectionProperties;

    public DatabaseConnector(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        databaseUser = (DatabaseUser) context.getBean("mock_database_user01");
        database = (Database) context.getBean("mock_database01");
    }
    
    public Connection getDatabaseConnection() throws SQLException
    {
        return DriverManager.getConnection(
                   "jdbc:" + database.getDbms() + "://" +
                   database.getServerName() +
                   ":" + database.getPortNumber() + "/",
                   getConnectionProperties());
    }
    
    private Properties getConnectionProperties()
    {
        connectionProperties = new Properties();
        connectionProperties.put("user", databaseUser.getName());
        connectionProperties.put("password", databaseUser.getPassword());
        return connectionProperties;    
    }
}
