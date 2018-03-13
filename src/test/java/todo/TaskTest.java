package todo;

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
public class TaskTest {

    @Test
    public void testTaskDateSetting() throws WrongToDoTypeException, ToDoDateDueNullException {
        Task taskBean = new Task();
        taskBean.setDateDue(setDateForTheTest());
        Assert.assertEquals(setDateForTheTest().getTime(), taskBean.getDateDue().getTime());
    }

    private Date setDateForTheTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    @Test(expected = ToDoDateDueNullException.class)
    public void testToDoDateDueNullException() throws ToDoDateDueNullException, WrongToDoTypeException {
        Task taskBean = new Task();
        taskBean.getDateDue();
    }

}
