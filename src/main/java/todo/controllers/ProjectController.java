package todo.controllers;

/**
 *
 * @author Labalve
 */
import java.sql.SQLException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todo.InvalidToDoIdException;
import todo.Project;
import todo.Task;
import todo.ToDoFactory;
import todo.DatabaseUserSelector;
import todo.ToDo;
import todo.ToDoPrinter;
import todo.User;
import todo.WrongToDoTypeException;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity get(@RequestParam("id") String id, @RequestHeader HttpHeaders headers) {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Project project;
        try {
            project = (Project) ToDoFactory.getBean("Project", id);
            if (!checkIsAuthor(headers.get("Authorization"), project)) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return new ResponseEntity(toDoPrinter.printToDo(project), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public ResponseEntity getAll(@RequestHeader HttpHeaders headers) {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        try {
            DatabaseUserSelector userSelector = new DatabaseUserSelector();
            String key = headers.get("Authorization").get(0);
            User user = userSelector.selectUserByKey(key);
            return new ResponseEntity(toDoPrinter.printAllProjects(user), HttpStatus.OK);
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity add(@RequestBody Project project, @RequestHeader HttpHeaders headers) throws SQLException, WrongToDoTypeException {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        DatabaseUserSelector userSelector = new DatabaseUserSelector();
        String key = headers.get("Authorization").get(0);
        User user = userSelector.selectUserByKey(key);
        project.setAuthor(user.getUuid());
        project.save();
        return new ResponseEntity("Project " + project.getTitle() + " saved.", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTaskList")
    public ResponseEntity getAttachedTasks(@RequestParam("id") String id, @RequestHeader HttpHeaders headers) {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Project project;
        try {
            project = (Project) ToDoFactory.getBean("Project", id);
            if (!checkIsAuthor(headers.get("Authorization"), project)) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        String tasks = "";
        tasks = project.getTaskList().stream().map((task) -> toDoPrinter.printToDo(task)).reduce(tasks, String::concat);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    private boolean checkIfAuthorized(List<String> authorization) {
        try {
            String key = authorization.get(0);
            return Authorizator.isUser(key);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkIsAuthor(List<String> authorization, ToDo toDo) {
        try {
            String key = authorization.get(0);
            return Authorizator.checkIsAuthor(key, toDo);
        } catch (NullPointerException | SQLException e) {
            return false;
        }
    }
}
