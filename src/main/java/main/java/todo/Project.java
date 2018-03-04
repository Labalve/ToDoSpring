package main.java.todo;

import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class Project extends ToDo {

    int importance;
    ArrayList<Task> tasks;
    
    Project(){
        tasks = new ArrayList<>();
    }
    
    public int getImportance() {
        return importance;
    }
    
    public void setImportance(int importance) {
        this.importance = importance;
    }
    
    public void attachTask(Task task){
        if(!(this.tasks.contains(task))){
            this.tasks.add(task);
            task.setProject(this);
        }
    }
    
    public ArrayList<Task> getTaskList(){
        return tasks;
//        String taskList = "";
//        taskList = tasks.stream().map((task) -> task.getTitle() + ": " + task.getDescription() + "\n").reduce(taskList, String::concat);
//        return taskList;
    }
    
}
