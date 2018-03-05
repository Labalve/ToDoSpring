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
        System.out.print("</todo>");
    }

    private String getXMLTaskStructure() {
        String taskStructure = "<task ";
        taskStructure += getXMLToDoDetails();
        taskStructure += "/>";
        return taskStructure;
    }

    private String getXMLProjectStructure() {
        String projectStructure = "<project ";
        projectStructure += getXMLToDoDetails() + ">";
        projectStructure += getXMLProjectsTaskList();
        projectStructure += "</project>";
        return projectStructure;
    }

    private String getXMLToDoDetails() {
        String taskDetails = "title=\"" + toDo.getTitle() + "\" description=\"" + toDo.getDescription() + "\"";
        taskDetails += getXMLTaskDateDueIfNotNull();
        taskDetails += " outcome=\"" + toDo.getOutcome() + "\"";
        return taskDetails;
    }

    private String getXMLTaskDateDueIfNotNull() {
        String taskDateDue = "";
        try {
            taskDateDue += " date_due=\"" + toDo.getDateDue() + "\"";
        } catch (ToDoDueDateNullException e) {
            ;
        }
        return taskDateDue;
    }

    private String getXMLProjectsTaskList() {
        ArrayList<Task> projectTasks = ((Project) toDo).getTaskList();
        String tasks = "";
        tasks = projectTasks.stream().map((task) -> "<task " + getXMLToDoDetails() + "/>").reduce(tasks, String::concat);
        return tasks;
    }
}
