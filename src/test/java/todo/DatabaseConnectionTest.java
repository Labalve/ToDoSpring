package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import junit.framework.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Labalve
 */
public class DatabaseConnectionTest {
    
    @Test
    public void testDatabaseConnectorCreation()
    {
        try {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        DatabaseUser databaseUser = (DatabaseUser) context.getBean("mock_database_user01");
        DatabaseServer database = (DatabaseServer) context.getBean("mock_database_server01");
        }
        catch(Exception e){
            fail(e.getClass().getEnclosingClass() + e.getMessage());
        }
    }
    
    @Test
    public void testDatabaseConnection() {
        try{
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getDatabaseConnection();
        } catch (SQLException e){
            fail("SQLException: " + e.getMessage()); 
        }
    }
    
}
