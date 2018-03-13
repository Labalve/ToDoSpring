package todo;

import java.util.Date;

enum Outcome {
    NEW, WIP, FAILED, SUCCEEDED, FROZEN
};

/**
 *
 * @author Labalve
 */
abstract class ToDo implements ToDoPrintable {

    String title;
    String description = "";
    Date dateDue;
    Outcome outcome = Outcome.NEW;
    String test;

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
