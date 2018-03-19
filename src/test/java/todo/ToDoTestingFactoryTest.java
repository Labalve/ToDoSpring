package todo;

import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author Labalve
 */

public class ToDoTestingFactoryTest {

    @Test
    public void testTaskBeanCreation() throws WrongToDoTypeException  {
        Task taskBean = (Task) ToDoTestingFactory.getBean("Task", "test_task01");
        Assert.assertEquals("test task01 title", taskBean.getTitle());
        Assert.assertEquals("test task01 description", taskBean.getDescription());
    }
        
    @Test
    public void testProjectBeanCreation() throws WrongToDoTypeException  {
        Project taskBean = (Project) ToDoTestingFactory.getBean("Project", "test_project01");
        Assert.assertEquals("test project01 title", taskBean.getTitle());
        Assert.assertEquals("test project01 description", taskBean.getDescription());
    }
    
    @Test(expected = WrongToDoTypeException.class)
    public void testWrongToDoTypeException() throws WrongToDoTypeException {
        Task taskBean = (Task) ToDoTestingFactory.getBean("WrongType", "test_task01");
    }    
    
}
