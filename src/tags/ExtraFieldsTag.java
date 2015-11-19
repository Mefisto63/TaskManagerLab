package tags;

import taskManager.tasks.AlarmClock;
import taskManager.tasks.Task;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by sergey on 08.03.15.
 */
public class ExtraFieldsTag extends SimpleTagSupport {
    private Task task;

    public void setTask(Object task) {
        this.task = (Task) task;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        if (task.getType() == 2) {
            AlarmClock clock = (AlarmClock) task;
            out.println("<hr>");
            out.println("<br><b>Count of repeats:</b>");
            out.println("<p>" + clock.getRepeatCount() + "</p>");
            out.println("<hr>");
            out.println("<br><b>Interval between repeats:</b>");
            out.println("<p>" + clock.getInterval() + "</p>");
        }
    }
}
