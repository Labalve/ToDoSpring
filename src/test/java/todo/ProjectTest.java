package todo;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;

/**
 *
 * @author Labalve
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ProjectTest {

    @Test
    public void testProjectDateSetting() throws WrongToDoTypeException, ToDoDateDueNullException  {
        Project projectBean = new Project();
        projectBean.setDateDue(setDateForTheTest());
        Assert.assertEquals(setDateForTheTest().getTime(), projectBean.getDateDue().getTime());
    }
    
    private Date setDateForTheTest(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    @Test
    public void testTaskBinding() {
        Project projectBean = new Project();
        Task taskBean01 = new Task();
        Task taskBean02 = new Task();
        projectBean.attachTask(taskBean01);
        taskBean02.setProject(projectBean);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskBean01);
        tasks.add(taskBean02);
        Assert.assertEquals(tasks, projectBean.getTaskList());
    }
    
    @Test(expected = ToDoDateDueNullException.class)
    public void testToDoDateDueNullException() throws ToDoDateDueNullException, WrongToDoTypeException {
        Project projectBean = new Project();
        projectBean.getDateDue();
    }
}
