package todo;

import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class ToDoPrinter {

    private ToDoPrintable toDo;

    public String printToDo(ToDoPrintable toDo) {
        this.toDo = toDo;
        try {
            return printXMLToDo();
        } catch (WrongToDoTypeException e){
            return e.getMessage();
        }
    }

    private String printXMLToDo() throws WrongToDoTypeException {
        if (toDo instanceof Task) {
            return getXMLTaskStructure();
        }
        else if (toDo instanceof Project) {
            return getXMLProjectStructure();
        }
        throw new WrongToDoTypeException("Wrong type provided.");
    }

    private String getXMLTaskStructure() {
        String taskStructure = "<task ";
        taskStructure += getXMLToDoDetails(toDo);
        taskStructure += "/>";
        return taskStructure;
    }

    private String getXMLProjectStructure() {
        String projectStructure = "<project ";
        projectStructure += getXMLToDoDetails(toDo) + ">";
        projectStructure += getXMLProjectsTaskList();
        projectStructure += "</project>";
        return projectStructure;
    }

    private String getXMLToDoDetails(ToDoPrintable toDoBeingChecked) {
        String toDoDetails = "title=\"" + toDoBeingChecked.getTitle() + "\" description=\"" + toDoBeingChecked.getDescription() + "\"";
        toDoDetails += getXMLToDoDateDueIfNotNull(toDoBeingChecked);
        toDoDetails += " outcome=\"" + toDoBeingChecked.getOutcome() + "\"";
        return toDoDetails;
    }

    private String getXMLToDoDateDueIfNotNull(ToDoPrintable toDoBeingChecked) {
        String toDoDateDue = "";
        try {
            toDoDateDue += " date_due=\"" + toDoBeingChecked.getDateDue() + "\"";
        } catch (ToDoDateDueNullException e) {
            ;
        }
        return toDoDateDue;
    }

    private String getXMLProjectsTaskList() {
        ArrayList<Task> projectTasks = ((Project) toDo).getTaskList();
        String tasks = "";
        tasks = projectTasks.stream().map((task) -> "<task " + getXMLToDoDetails(task) + "/>").reduce(tasks, String::concat);
        return tasks;
    }
}
