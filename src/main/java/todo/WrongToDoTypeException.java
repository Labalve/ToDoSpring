package todo;

/**
 *
 * @author Labalve
 */
public class WrongToDoTypeException extends Exception{
    private String errorMessage;
    public WrongToDoTypeException(String errorMessage){
        this.errorMessage = errorMessage;
    }
    @Override
    public String getMessage(){
        return this.errorMessage;
    }
}
