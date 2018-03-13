package todo;

import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class ToDoPrinter {

    private ToDoPrintable toDo;

    public void printToDo(ToDoPrintable toDo) {
        this.toDo = toDo;
        printXMLToDo();
    }

    private void printXMLToDo() {
        if (toDo instanceof Task) {
            System.out.print(getXMLTaskStructure());
        }
        if (toDo instanceof Project) {
            System.out.print(getXMLProjectStructure());
        }
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
