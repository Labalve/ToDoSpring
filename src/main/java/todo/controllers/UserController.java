package todo.controllers;

import java.sql.SQLException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import todo.Project;
import todo.User;
import todo.WrongToDoTypeException;

/**
 *
 * @author Labalve
 */
@RestController
@RequestMapping("/getKey")
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity add(@RequestHeader HttpHeaders headers) throws SQLException, WrongToDoTypeException {
        if (!checkIfAdmin(headers.get("Authorization"))) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        try {
            User user = new User();
            user.save();
            return new ResponseEntity(user.getKey(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println("Adding user failed with message: " + e.getMessage());
            return new ResponseEntity("Try again later.", HttpStatus.FORBIDDEN);
        }
    }

    private boolean checkIfAdmin(List<String> authorization) {
        try {
            String key = authorization.get(0);
            return Authorizator.isAdmin(key);
        } catch (NullPointerException e) {
            return false;
        }
    }

}
