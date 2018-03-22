package todo;

import java.util.Date;

/**
 *
 * @author Labalve
 */
interface ToDoPrintable {

    String getTitle();

    String getDescription();

    static String[] getAllUuids(User user) {
        return null;
    }

    Date getDateDue() throws ToDoDateDueNullException;

    Outcome getOutcome();
}
