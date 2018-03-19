package todo;

/**
 *
 * @author Labalve
 */
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
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

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Task> update(@RequestBody Task task) throws SQLException, WrongToDoTypeException {

        System.out.println("GET PROJECT UUID " + task.getProjectUuid());
        task.setProject(task.getProjectUuid());
        task.save();
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
