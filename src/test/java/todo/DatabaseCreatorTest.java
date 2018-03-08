package todo;

import java.sql.SQLException;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Labalve
 */
public class DatabaseCreatorTest {

    @Test
    public void testDatabaseConnectorCreation() {
        try {
            DatabaseCreator.createDatabase();
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
