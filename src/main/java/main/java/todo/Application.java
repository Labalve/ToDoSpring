package main.java.todo;

import java.util.Calendar;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) throws WrongToDoTypeException {

        ToDoPrinter toDoPrinter = (ToDoPrinter) ToDoFactory.getToDoPrinter();

        Task taskBean = (Task) ToDoFactory.getBean("Task", "task01");
        Project projectBean = (Project) ToDoFactory.getBean("Project", "project01");
        
        Task taskBean2 = new Task();
        taskBean2.setTitle("Go shopping");
        taskBean2.setDescription("Don't forget to buy soy milk!");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 6);
        projectBean.setDateDue(cal.getTime());

        taskBean.setProject(projectBean);
        projectBean.attachTask(taskBean2);
        toDoPrinter.printToDo(projectBean);
    }
}
