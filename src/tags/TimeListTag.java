package tags;

import locator.ServiceLocator;
import taskManager.TaskData;
import taskManager.controller.Controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by sergey on 08.03.15.
 */
public class TimeListTag extends SimpleTagSupport {
    private long idTask;

    public void setIdTask(Object idTask) {
        this.idTask =(long) idTask;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        JspWriter out = getJspContext().getOut();
        out.println("<ul>");
        for (TaskData data : controller.getWorkingTasks()) {
            if (data.getIdTask() == idTask){
                out.println("<li>" + data.getDate() + "</li>");
            }
        }
        out.println("</ul>");
    }
}
