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
import todo.DatabaseUserSelector;
import todo.InvalidToDoIdException;
import todo.Project;
import todo.Task;
import todo.ToDo;
import todo.ToDoFactory;
import todo.ToDoPrinter;
import todo.User;
import todo.WrongToDoTypeException;

@RestController
@RequestMapping("/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity get(@RequestParam("id") String id, @RequestHeader HttpHeaders headers) {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Task task;
        try {
            task = (Task) ToDoFactory.getBean("Task", id);
            if (!checkIsAuthor(headers.get("Authorization"), task)) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (SQLException | InvalidToDoIdException | WrongToDoTypeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        return new ResponseEntity(toDoPrinter.printToDo(task), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public ResponseEntity getAll(@RequestHeader HttpHeaders headers){
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        try {
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        DatabaseUserSelector userSelector = new DatabaseUserSelector();
        String key = headers.get("Authorization").get(0);
        User user = userSelector.selectUserByKey(key);
        return new ResponseEntity(toDoPrinter.printAllTasks(user), HttpStatus.OK);
        } catch (SQLException | WrongToDoTypeException | InvalidToDoIdException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Task> add(@RequestBody Task task, @RequestHeader HttpHeaders headers) throws SQLException, WrongToDoTypeException {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        task.setAuthor(headers.get("Authorization").get(0));
        task.setProject(task.getProjectUuid());
        task.save();
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/attachToProject")
    public ResponseEntity attachToProject(@RequestParam("id") String id, @RequestParam("projectId") String projectId, @RequestHeader HttpHeaders headers) {
        if (!checkIfAuthorized(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Task task;
        Project project;
        try {
            task = (Task) ToDoFactory.getBean("Task", id);
            project = (Project) ToDoFactory.getBean("Project", projectId);
            if (!checkIsAuthor(headers.get("Authorization"), task) || !checkIsAuthor(headers.get("Authorization"), project)) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
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
