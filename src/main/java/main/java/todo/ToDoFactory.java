package main.java.todo;

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

    ;
    
    public static ToDoFactory getInstance() {
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

    public static ToDoPrinter getToDoPrinter() {
        ToDoFactory toDoFactory = ToDoFactory.getInstance();
        return (ToDoPrinter) toDoFactory.context.getBean("todoprinter");
    }

    public Task getTaskBean(String guid) {
        Task bean = (Task) context.getBean(guid);
        return bean;
    }

    public Project getProjectBean(String guid) {
        Project bean = (Project) context.getBean(guid);
        return bean;
    }

}
