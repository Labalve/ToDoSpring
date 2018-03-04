package main.java.todo;

import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class ToDoPrinter {

    private static ToDoPrinter instance;

    private ToDoPrinter() {
    }

    public void printToDo(ToDoPrintable toDo) {
        printXMLToDo(toDo);
    }

    private void printXMLToDo(ToDoPrintable toDo) {
        System.out.print("<todo");
        System.out.print(getXMLToDoDetails(toDo));
        System.out.print(">");
        if (toDo instanceof Project) {
            System.out.print(getXMLProjectsTaskList((Project) toDo));
        }
        System.out.print("</todo>");
    }

    private String getXMLToDoDetails(ToDoPrintable toDo) {
        String taskDetails = " title=\"" + toDo.getTitle() + "\" description=\"" + toDo.getDescription() + "\"";
        taskDetails += getXMLTaskDateDueIfNotNull(toDo);
        taskDetails += " outcome=\"" + toDo.getOutcome() + "\"";
        return taskDetails;
    }

    private String getXMLTaskDateDueIfNotNull(ToDoPrintable toDo) {
        String taskDateDue = "";
        try {
            taskDateDue += " date_due=\"" + toDo.getDateDue() + "\"";
        } catch (ToDoDueDateNullException e) {
            ;
        }
        return taskDateDue;
    }

    private String getXMLProjectsTaskList(Project toDo) {
        ArrayList<Task> projectTasks = toDo.getTaskList();//getTasksList();
        String tasks = "";
        tasks = projectTasks.stream().map((task) -> "<task " + getXMLToDoDetails(task) + "/>").reduce(tasks, String::concat);
        return tasks;
    }
}
