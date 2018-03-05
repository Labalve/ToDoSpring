package todo;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) {

        ToDoPrinter toDoPrinter = new ToDoPrinter();

    }
}
