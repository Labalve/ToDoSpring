package todo;

/**
 *
 * @author Labalve
 */
public class Task extends ToDo {

    private String project_id;
    Project project;

    void setProject(Project project){
        this.project = project;
        project.attachTask(this);
    }
    
    String getProject() {
        return project_id;
    }

}
