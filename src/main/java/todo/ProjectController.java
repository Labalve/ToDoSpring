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
@RequestMapping("/project")
public class ProjectController {

    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}")
    public String getProject(@PathVariable String projectId) throws SQLException {
        Project project;
        try {
            project = (Project) ToDoFactory.getBean("Project", projectId);
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return e.getMessage();
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return toDoPrinter.printToDo(project);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Project> add(@RequestBody Project project) throws SQLException, WrongToDoTypeException {
        project.save();
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}