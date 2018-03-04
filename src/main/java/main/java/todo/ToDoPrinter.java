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
    
    private void printXMLToDo(ToDoPrintable toDo){
            System.out.print("<todo");
            System.out.print(" title=\"" + toDo.getTitle() + "\" description=\"" + toDo.getDescription() + "\"");
        try {
            System.out.print(" date_due=\"" + toDo.getDateDue() +"\"");
        } catch (ToDoDueDateNullException e) {

        } finally {
            System.out.print(" outcome=\"" + toDo.getOutcome() + "\">");
            if (toDo instanceof Project) {
                System.out.print(getXMLProjectList((Project) toDo));
            }
            System.out.print("</todo>");
        }
    } 

    private String getXMLProjectList(Project toDo) {
        ArrayList<Task> projectTasks = toDo.getTaskList();//getTasksList();
        String tasks = "";
        tasks = projectTasks.stream().map((task) -> "<task title=\"" + task.getTitle() + "\" description=\"" + task.getDescription() + "\"/>").reduce(tasks, String::concat);
        return tasks;
    }
}
