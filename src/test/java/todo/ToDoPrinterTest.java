package todo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Labalve
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {AppConfig.class})
public class ToDoPrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
        public void testPrintingProjectWithoutTaskListXMLFormat() throws WrongToDoTypeException {
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        Project projectBean = (Project) ToDoTestingFactory.getBean("Project", "test_project01");
        String response = toDoPrinter.printToDo(projectBean);
        Assert.assertEquals(getXMLProjectStructure(projectBean), response);
    }
    
    @Test
    public void testPrintingProjectWithTaskListXMLFormat() throws WrongToDoTypeException {
        ToDoPrinter toDoPrinter = new ToDoPrinter();
        Task taskBean01 = (Task) ToDoTestingFactory.getBean("Task", "test_task01");
        Task taskBean02 = (Task) ToDoTestingFactory.getBean("Task", "test_task02");
        Project projectBean = (Project) ToDoTestingFactory.getBean("Project", "test_project01");
        taskBean01.setProject(projectBean);
        projectBean.attachTask(taskBean02);
        String response = toDoPrinter.printToDo(projectBean);
        Assert.assertEquals(getExpectedTaskListPrint(projectBean), response);
    }
    
    private String getExpectedTaskListPrint(Project projectBean) {
        return getXMLProjectStructure(projectBean);
    }

    private String getXMLProjectStructure(Project projectBean) {
        String projectStructure = "<project>";
        projectStructure += getXMLToDoDetails(projectBean);
        projectStructure += getXMLProjectsTaskList(projectBean);
        projectStructure += "</project>";
        return projectStructure;
    }
    
    private String getXMLToDoDetails(ToDo toDo) {
        String taskDetails = "<title>" + toDo.getTitle() + "</title><description>" + toDo.getDescription() + "</description>";
        taskDetails += getXMLTaskDateDueIfNotNull(toDo);
        taskDetails += "<outcome>" + toDo.getOutcome() + "</outcome>";
        return taskDetails;
    }

    private String getXMLTaskDateDueIfNotNull(ToDo toDo) {
        String taskDateDue = "";
        try {
            taskDateDue += "<date_due>" + toDo.getDateDue() + "</date_due>";
        } catch (ToDoDateDueNullException e) {
            ;
        }
        return taskDateDue;
    }

    private String getXMLProjectsTaskList(Project projectBean) {
        ArrayList<Task> projectTasks = projectBean.getTaskList();
        String tasks = "";
        tasks = projectTasks.stream().map((task) -> "<task>" + getXMLToDoDetails(task) + "</task>").reduce(tasks, String::concat);
        return tasks;
    }

}

