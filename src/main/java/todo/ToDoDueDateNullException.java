package todo;

/**
 *
 * @author Labalve
 */
public class ToDoDueDateNullException extends Exception {
    private String errorMessage;
    ToDoDueDateNullException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    @Override
    public String getMessage(){
        return errorMessage;
    }
}
