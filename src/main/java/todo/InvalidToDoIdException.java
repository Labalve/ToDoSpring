package todo;

/**
 *
 * @author Labalve
 */
public class InvalidToDoIdException extends Exception {

    private final String message;
    
    public InvalidToDoIdException(String toDoType, String uuid) {
        message = "There's no " + toDoType + " with id " + uuid;
    }
    
    @Override
    public String getMessage(){
        return message;
    }

}
