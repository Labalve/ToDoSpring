package todo;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
//import java.util.UUID.*;

enum Outcome {
    NEW, WIP, FAILED, SUCCEEDED, FROZEN
};

/**
 *
 * @author Labalve
 */
abstract class ToDo implements ToDoPrintable {


    private String uuid;
    String title;
    String description = "";
    Date dateDue;
    Outcome outcome = Outcome.NEW;
    String test;
    
    public ToDo() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }

    public void save() throws SQLException {
        DatabaseToDoInserter databaseToDoInserter = new DatabaseToDoInserter();
        databaseToDoInserter.saveToDo(this);
    }
    
    //  hack to go around different testing database
    public void testSave() throws SQLException {
        DatabaseToDoInserter databaseToDoInserter = new DatabaseToDoInserter("mock_database01");
        databaseToDoInserter.saveToDo(this);
    }
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateDue() throws ToDoDateDueNullException {
        if (dateDue == null) {
            throw new ToDoDateDueNullException(title + " dateDue is null");
        } else {
            return dateDue;
        }
    }

    public Outcome getOutcome() {
        return outcome;
    }
    
    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
