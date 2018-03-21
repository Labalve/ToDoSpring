package todo;

/**
 *
 * @author Labalve
 */
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String get(@RequestParam("id") String id) {
        Task task;
        try {
            task = (Task) ToDoFactory.getBean("Task", id);
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return e.getMessage();
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return toDoPrinter.printToDo(task);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Task> add(@RequestBody Task task) throws SQLException, WrongToDoTypeException {
        task.setProject(task.getProjectUuid());
        task.save();
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/attachToProject")
    public ResponseEntity attachToProject(@RequestParam("id") String id, @RequestParam("projectId") String projectId) {
        Task task;
        Project project;
        try {
            task = (Task) ToDoFactory.getBean("Task", id);
            task.setProject(projectId);
            task.save();
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            if (e instanceof SQLException) {
                return new ResponseEntity("Probably wrong task or project id provided.", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity("Attaching failed.", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity("Attached task with id '" + id + "' to project with id '" + projectId + "'", HttpStatus.OK);
    }
}
