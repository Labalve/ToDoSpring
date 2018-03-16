package todo;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Labalve
 */
public class ToDoFactory {

    private static ToDoFactory instance;
    private AbstractApplicationContext context;

    private ToDoFactory() {
        this.context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    private static ToDoFactory getInstance() {
        if (ToDoFactory.instance == null) {
            ToDoFactory.instance = new ToDoFactory();
        }
        return ToDoFactory.instance;
    }

    public static ToDo getBean(String type, String guid) throws WrongToDoTypeException {
        ToDoFactory toDoFactory = ToDoFactory.getInstance();
        switch (type) {
            case "Task":
                return toDoFactory.getTaskBean(guid);
            case "Project":
                return toDoFactory.getProjectBean(guid);
            default:
                throw new WrongToDoTypeException(type + " is not a ToDo type");
        }
    }

    public Task getTaskBean(String guid) {
        Task bean = (Task) context.getBean(guid);
        return bean;
    }

    public Project getProjectBean(String guid) {
        Project bean = (Project) context.getBean(guid);
        return bean;
    }
    
    public static ToDo getContextBean(String type, String guid) throws WrongToDoTypeException {
        ToDoFactory toDoFactory = ToDoFactory.getInstance();
        switch (type) {
            case "Task":
                return toDoFactory.getContextTaskBean(guid);
            case "Project":
                return toDoFactory.getContextProjectBean(guid);
            default:
                throw new WrongToDoTypeException(type + " is not a ToDo type");
        }
    }

    public Task getContextTaskBean(String guid) {
        Task bean = (Task) context.getBean(guid);
        return bean;
    }

    public Project getContextProjectBean(String guid) {
        Project bean = (Project) context.getBean(guid);
        return bean;
    }

}
