package tags;

import locator.ServiceLocator;
import taskManager.Group;
import taskManager.User;
import taskManager.manager.LifeCycleManager;
import taskManager.tasks.Task;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by sergey on 21.03.15.
 */
public class TreeViewTag extends SimpleTagSupport {
    private long id;
    public static final String marker = "<a href=\"#\" class=\"sc\" onclick=\"return UnHide(this)\">&#9658;</a>";
    public static final String startBlock = "<div><p>";
    public static final String endBlock = "</p></div>";


    public void setidUser(String id) {
        this.id = Long.parseLong(id);
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        LifeCycleManager lfm = ServiceLocator.getInstance().getLifeCycleManager();
        Group group = lfm.getGroup(id);
        out.println("<div class=\"treeview\"><ul><li>");
        out.println(startBlock);
        out.println(marker + group.getName());
        out.println(endBlock);
        out.println("<ul>");
        out.println(getTree(group.getId(), lfm.getLevel(group.getId())));
//        List<Task> tasks = lfm.getTasksOfCurGroup(group.getId());
//        List<Group> groups = lfm.getChildGroup(group.getId());
//        List<User> users = lfm.getUsersInGroup(group.getId());
//        for (Group group1 : groups) {
//            out.println("<li class = 'cl'>");
//            out.println(startBlock);
//            out.println(marker + group1.getName());
//            out.println(endBlock);
//            out.println(getTree(group1.getId(),lfm.getLevel(group.getId())));
//            out.println("</li>");
//        }
//        out.println("<li class = 'cl'>");
//        out.println(startBlock);
//        out.println(marker + "Users:");
//        out.println(endBlock);
//        out.println("<ul>");
//        for (User user : users) {
//            out.println("<li class = 'cl'>");
//            out.println(startBlock);
//            out.println(marker + user.getName());
//            out.println(endBlock);
//            out.println("<ul>");
//            int i = 0;
//            for (Task task : lfm.getTasksOfUser(user.getId())) {
//                out.println("<li>");
//                out.println(startBlock + "<a href='/myApp/task?id=" +task.getId()+"'>"
//                        + (i+1) + ") " + task.getName()+": " + task.getDescription() + "</a>" + endBlock);
//                out.println("</li>");
//                i++;
//            }
//
//            out.println("</ul>");
//            out.println("</li>");
//        }
//        out.println("</ul>");
//        out.println("</li>");
//
//        for (int i = 0; i < tasks.size(); i++) {
//            Task task = tasks.get(i);
//            out.println("<li>");
//            out.println(startBlock + "<a href='/myApp/task?id=" +task.getId()+"'>"
//                    + (i+1) + ") " + task.getName()+": " + task.getDescription() + "</a>" + endBlock);
//            out.println("</li>");
//        }
        out.println("</ul>");
        out.println("</li></ul></div>");

    }

    private String getTree(long id, int level) throws IOException {
        String str = "";
        if (level != -1) {
            str = "<ul>";
            LifeCycleManager lfm = ServiceLocator.getInstance().getLifeCycleManager();
            List<Task> tasks = lfm.getTasksOfCurGroup(id);
            List<Group> groups = lfm.getChildGroup(id);
            List<User> users = lfm.getUsersInGroup(id);
            for (Group group1 : groups) {
                str += "<li class = 'cl'>";
                str += startBlock;
                str += marker + group1.getName();
                str += endBlock;
                level -= 1;
                str += getTree(group1.getId(), level);
                str += "</li>";
            }
            str += "<li class = 'cl'>";
            str += startBlock;
            str += marker + "Users:";
            str += endBlock;
            str += "<ul>";
            for (User user : users) {
                str += "<li class = 'cl'>";
                str += startBlock;
                str += marker + user.getName();
                str += endBlock;
                str += "<ul>";
                int i = 0;
                for (Task task : lfm.getTasksOfUser(user.getId())) {
                    str += "<li>";
                    str += startBlock + "<a href='/myApp/task?id=" + task.getId() + "'>"
                            + (i + 1) + ") " + task.getName() + ": " + task.getDescription() + "</a>" + endBlock;
                    str += "</li>";
                    i++;
                }

                str += "</ul>";
                str += "</li>";
            }
            str += "</ul>";
            str += "</li>";

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                str += "<li>";
                str += startBlock + "<a href='/myApp/task?id=" + task.getId() + "'>"
                        + (i + 1) + ") " + task.getName() + ": " + task.getDescription() + "</a>" + endBlock;
                str += "</li>";
            }
            str += "</ul>";

        }
        return str;
    }
}
