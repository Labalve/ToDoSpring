package todo;

import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class Task extends ToDo {

    Project project;
    String project_uuid;

    void setProject(Project project){
        this.project = project;
        this.project_uuid = project.getUuid();
        project.attachTask(this);
    }
    
    void setProject(String uuid) throws WrongToDoTypeException, SQLException{
        try {
            setProject((Project) ToDoFactory.getBean("Project", uuid));
        } catch (InvalidToDoIdException e) {
            System.out.println(e.getMessage());
        }
    }
    
    Project getProject() {
        return project;
    }

}
