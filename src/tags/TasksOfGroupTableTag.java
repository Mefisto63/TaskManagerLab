package tags;

import locator.ServiceLocator;
import taskManager.TaskData;
import taskManager.controller.Controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by sergey on 13.03.15.
 */
public class TasksOfGroupTableTag extends SimpleTagSupport {
    private long id;

    public void setidUser(String id) {
        this.id = Long.parseLong(id);
    }

    @Override
    public void doTag() throws JspException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        JspWriter out = getJspContext().getOut();
        out.println("<tr>");
        out.println("<th>Task's number</th>");
        out.println("<th>Name</th>");
        out.println("<th>Description</th>");
        out.println("<th>Time</th>");
        out.println("</tr>");
        int i = 1;
        for (TaskData data : controller.getTasksOfGroup(id)) {
            out.println("<tr><td><a href='/myApp/task?id="+ data.getIdTask() + "' target='_self'>" + i +
                    "</a></td>");
            out.println("<td>" + data.getName() + "</td>");
            out.println("<td>" + data.getDescription() + "</td>");
            if (data.getDate() != null) {
                out.println("<td>" + data.getDate() + "</td>");
            } else {
                out.println("<td>empty</td>");
            }
            i++;
        }
    }

}
