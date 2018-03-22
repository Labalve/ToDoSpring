package todo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Labalve
 */
public class Project extends ToDo {

    ArrayList<Task> tasks;
    
    Project(){
        super();
        tasks = new ArrayList<>();
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
    
    public static String[] getAllUuids(User user) throws SQLException{
        DatabaseToDoSelector databaseToDoSelector = new DatabaseToDoSelector();
        return databaseToDoSelector.getAllProjectUuids(user);
    }
    
}
