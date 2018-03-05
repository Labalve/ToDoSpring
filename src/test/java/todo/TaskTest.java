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
public class TaskTest {

    @Test
    public void testTaskDateSetting() throws WrongToDoTypeException, ToDoDueDateNullException {
        Task taskBean = new Task();
        taskBean.setDateDue(setDateForTheTest());
        Assert.assertEquals(setDateForTheTest(), taskBean.getDateDue());
    }

    private Date setDateForTheTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    @Test(expected = ToDoDueDateNullException.class)
    public void testToDoDueDateNullException() throws ToDoDueDateNullException, WrongToDoTypeException {
        Task taskBean = new Task();
        taskBean.getDateDue();
    }

}
