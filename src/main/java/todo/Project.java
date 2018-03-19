package todo;

import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class Project extends ToDo {

    int importance;
    ArrayList<Task> tasks;
    
    Project(){
        super();
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
    
    public void attachTasks(ArrayList<Task> attachedTasks) {
        attachedTasks.forEach((task) -> {
            attachTask(task);
        });
    }
    
    public ArrayList<Task> getTaskList(){
        return tasks;
    }
    
}
