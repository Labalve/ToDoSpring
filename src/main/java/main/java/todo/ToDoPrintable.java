package main.java.todo;

import java.util.Date;

/**
 *
 * @author Labalve
 */
interface ToDoPrintable {
    String getTitle();
    String getDescription();
    Date getDateDue() throws ToDoDueDateNullException;
    Outcome getOutcome();
}
