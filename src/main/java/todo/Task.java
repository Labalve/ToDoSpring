package todo;

import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class Task extends ToDo {

    Project project;
    String projectUuid;

    public void setProject(Project project) {
        this.project = project;
        this.projectUuid = project.getUuid();
        project.attachTask(this);
    }

    public void setProject(String uuid) throws WrongToDoTypeException, SQLException {
        this.projectUuid = uuid;
    }

    Project getProject() {
        if (project == null) {
            try {
                setProject((Project) ToDoFactory.getBean("Project", projectUuid));
            } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
                System.out.println(e.getMessage());
            }
        }
        return project;
    }

    void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getProjectUuid() {
        return projectUuid;
    }
        
    public static String[] getAllUuids(User user) throws SQLException{
        DatabaseToDoSelector databaseToDoSelector = new DatabaseToDoSelector();
        return databaseToDoSelector.getAllTaskUuids(user);
    }

}
