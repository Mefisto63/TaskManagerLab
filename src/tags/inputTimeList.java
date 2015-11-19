package tags;

import locator.ServiceLocator;
import taskManager.TaskData;
import taskManager.controller.Controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by sergey on 10.03.15.
 */
public class inputTimeList extends SimpleTagSupport {
    private long idTask;

    public void setIdTask(Object idTask) {
        this.idTask =(long) idTask;
    }


    @Override
    public void doTag() throws JspException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        JspWriter out = getJspContext().getOut();
        out.println("<ul>");
        int i = 1;
        for (TaskData data : controller.getWorkingTasks()) {
            if (data.getIdTask() == idTask){
                out.println("<input type='text' name='" + i + "_t' value ='" + data.getIdTrigger() +"' style=\"visibility: hidden\"");
                i++;
                out.print("<li><input id=\"date\" name = \"" + i + "_date");
                i++;
                out.print("\" type=\"date\"><input id=\"time\" name = \"" + i + "_time\" type=\"time\"></li>");
                i++;
            }
        }
        out.println("</ul>");
    }
}
