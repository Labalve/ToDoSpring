package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Labalve
 */
public class DatabaseConnectionTest {
    
    @Test
    public void testDatabaseCreation() {
        try{
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getDatabaseConnection();
        } catch (SQLException e){
            fail(e.getMessage());
        }
    }
    
}
