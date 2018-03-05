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
public class ProjectTest {

    @Test
    public void testProjectDateSetting() throws WrongToDoTypeException, ToDoDueDateNullException  {
        Project projectBean = new Project();
        projectBean.setDateDue(setDateForTheTest());
        Assert.assertEquals(setDateForTheTest(), projectBean.getDateDue());
    }
    
    private Date setDateForTheTest(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    @Test(expected = ToDoDueDateNullException.class)
    public void testToDoDueDateNullException() throws ToDoDueDateNullException, WrongToDoTypeException {
        Project projectBean = new Project();
        projectBean.getDateDue();
    }
}
