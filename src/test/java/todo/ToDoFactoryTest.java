package todo;

import todo.AppConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ToDoFactoryTest {

    @org.junit.Test
    public void testTaskBeanCreation() throws WrongToDoTypeException  {
        Task taskBean = (Task) ToDoFactory.getBean("Task", "test_task01");
        Assert.assertEquals("test task01 title", taskBean.getTitle());
        Assert.assertEquals("test task01 description", taskBean.getDescription());
    }
        
    @org.junit.Test
    public void testProjectBeanCreation() throws WrongToDoTypeException  {
        Project taskBean = (Project) ToDoFactory.getBean("Project", "test_project01");
        Assert.assertEquals("test project01 title", taskBean.getTitle());
        Assert.assertEquals("test project01 description", taskBean.getDescription());
    }
    
    @Test(expected = WrongToDoTypeException.class)
    public void testWrongToDoTypeException() throws WrongToDoTypeException {
        Task taskBean = (Task) ToDoFactory.getBean("WrongType", "test_task01");
    }    
    
}
