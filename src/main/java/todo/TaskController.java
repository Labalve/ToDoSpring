package todo;

/**
 *
 * @author Labalve
 */
import java.sql.SQLException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task/{taskId}")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET)
    public String getTask(@PathVariable String taskId) throws SQLException {
        Task task;
        try {
            task = (Task) ToDoFactory.getBean("Task", taskId);
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return e.getMessage();
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return toDoPrinter.printToDo(task);
    }

}
