package tags;

import locator.ServiceLocator;
import taskManager.controller.Controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sergey on 13.03.15.
 */
public class GroupCheckBoxTag extends SimpleTagSupport {
    private long id;

    public void setId(Object id) {
        this.id = (long) id;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        JspWriter out = getJspContext().getOut();
        Map<Long, String> map = controller.getGroups(id);
        int i = 0;
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            out.println("<label><input type='checkbox' name='" + i++ + "_group' value='" + entry.getKey() + "'>" + entry.getValue() + "</label>");
        }
    }
}
