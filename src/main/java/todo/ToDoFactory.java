package todo;

import java.sql.SQLException;

/**
 *
 * @author Labalve
 */
public class ToDoFactory {

    private static ToDoFactory instance;

    private ToDoFactory() {
        
    }

    private static ToDoFactory getInstance() {
        if (ToDoFactory.instance == null) {
            ToDoFactory.instance = new ToDoFactory();
        }
        return ToDoFactory.instance;
    }
    
    public static ToDo getBean(String type, String uuid) throws WrongToDoTypeException, SQLException, InvalidToDoIdException {
        ToDoFactory toDoFactory = ToDoFactory.getInstance();
        switch (type) {
            case "Task":
                return toDoFactory.getTaskBean(uuid);
            case "Project":
                return toDoFactory.getProjectBean(uuid);
            default:
                throw new WrongToDoTypeException(type + " is not a ToDo type");
        }
    }

    public Task getTaskBean(String uuid) throws SQLException, InvalidToDoIdException {
        DatabaseToDoSelector databaseToDoSelector = new DatabaseToDoSelector();
        Task bean = (Task) databaseToDoSelector.getTask(uuid);
        return bean;
    }

    public Project getProjectBean(String uuid) throws SQLException, InvalidToDoIdException {
        DatabaseToDoSelector databaseToDoSelector = new DatabaseToDoSelector();
        Project bean = (Project) databaseToDoSelector.getProject(uuid);
        return bean;
    }
}
