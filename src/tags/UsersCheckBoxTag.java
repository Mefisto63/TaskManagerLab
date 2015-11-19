package tags;

import locator.ServiceLocator;
import taskManager.manager.LifeCycleManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sergey on 22.03.15.
 */
public class UsersCheckBoxTag extends SimpleTagSupport {
    private long id;

    public void setId(Object id) {
        this.id = (long) id;
    }

    @Override
    public void doTag() throws JspException, IOException {
        LifeCycleManager lcm = ServiceLocator.getInstance().getLifeCycleManager();
        JspWriter out = getJspContext().getOut();
        Map<Long, String> map = lcm.getUsers(id);
        int i = 0;
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            out.println("<label><input type='checkbox' name='" + i++ + "_user' value='" + entry.getKey() + "'>" + entry.getValue() + "</label>");
        }
    }
}
