package todo;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author Labalve
 */
public class ToDoTestingFactory {
    
    private static ToDoTestingFactory instance;
    private AbstractApplicationContext context;

    private ToDoTestingFactory() {
        this.context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    private static ToDoTestingFactory getInstance() {
        if (ToDoTestingFactory.instance == null) {
            ToDoTestingFactory.instance = new ToDoTestingFactory();
        }
        return ToDoTestingFactory.instance;
    }

    public static ToDo getBean(String type, String guid) throws WrongToDoTypeException {
        ToDoTestingFactory toDoTestingFactory = ToDoTestingFactory.getInstance();
        switch (type) {
            case "Task":
                return toDoTestingFactory.getContextTaskBean(guid);
            case "Project":
                return toDoTestingFactory.getContextProjectBean(guid);
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
