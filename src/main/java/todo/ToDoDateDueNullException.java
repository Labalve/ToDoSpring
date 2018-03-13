package todo;

/**
 *
 * @author Labalve
 */
public class ToDoDateDueNullException extends Exception {
    private String errorMessage;
    ToDoDateDueNullException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    @Override
    public String getMessage(){
        return errorMessage;
    }
}
