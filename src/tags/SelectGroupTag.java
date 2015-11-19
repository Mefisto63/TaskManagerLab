package tags;

import locator.ServiceLocator;
import taskManager.controller.Controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sergey on 13.03.15.
 */
public class SelectGroupTag extends SimpleTagSupport {
    private long id;

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    @Override
    public void doTag() throws JspException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        Map<Long, String> groups = controller.getGroups(id);
        Iterator<Map.Entry<Long, String>> it = groups.entrySet().iterator();
        it.next();
        it.remove();
        JspWriter out = getJspContext().getOut();
        out.println("<select id='groups' name='groups'>");
        for (Map.Entry<Long, String> entry : groups.entrySet()) {
            out.println("<option value='" + entry.getKey() + "'>" + entry.getValue() + "</option>");
        }
        out.println("</select>");
    }
}
