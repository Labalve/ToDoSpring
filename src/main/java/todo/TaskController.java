package todo;

/**
 *
 * @author Labalve
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class TaskController {

    @RequestMapping("/task")
    public String test() throws SQLException {
        Task task;
        try {
            task = (Task) ToDoFactory.getBean("Task", "test_task01");
        } catch (WrongToDoTypeException e) {
            return e.getMessage();
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return toDoPrinter.printToDo(task);
    }

}