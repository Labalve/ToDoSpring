package todo;

import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Labalve
 */
public class DatabaseConnectorTest {
    
    @Test
    public void testDatabaseConnectorCreation()
    {
        try {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        DatabaseUser databaseUser = (DatabaseUser) context.getBean("database_user01");
        DatabaseServer database = (DatabaseServer) context.getBean("database_server01");
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
