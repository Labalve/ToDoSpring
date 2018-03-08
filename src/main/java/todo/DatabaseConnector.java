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
    static private String defaultDatabaseServerId = "mock_database_server01";
    
    private DatabaseUser databaseUser;
    private DatabaseServer databaseServer;
    private Properties connectionProperties;

    public DatabaseConnector(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        databaseUser = (DatabaseUser) context.getBean(defaultDatabaseUserId);
        databaseServer = (DatabaseServer) context.getBean(defaultDatabaseServerId);
    }
    
    public DatabaseConnector(String databaseUserId, String databaseServerId){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        databaseUser = (DatabaseUser) context.getBean(databaseUserId);
        databaseServer = (DatabaseServer) context.getBean(databaseServerId);
    }
    
    public Connection getDatabaseConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:" + databaseServer.getDbms() + "://" +
                   databaseServer.getServerName() +
                   ":" + databaseServer.getPortNumber() + "/",
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
