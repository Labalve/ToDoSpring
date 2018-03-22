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
public abstract class ToDo implements ToDoPrintable {

    private String uuid;
    private String title;
    private String description = "";
    private Date dateDue;
    private Outcome outcome = Outcome.NEW;
    private String authorUuid;
    private User author;

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

    public String getAuthorUuid() {
        return authorUuid;
    }

    User getAuthor() {
        if (author == null) {
            try {
                DatabaseUserSelector userSelector = new DatabaseUserSelector();
                User user = userSelector.selectUser(this.authorUuid);
                setAuthor(user);
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        this.authorUuid = author.getUuid();
    }

    public void setAuthor(String authorUuid) {
        this.authorUuid = authorUuid;
    }

    public void setProject(String uuid) throws WrongToDoTypeException, SQLException {
        this.authorUuid = uuid;
    }

}
