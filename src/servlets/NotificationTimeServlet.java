package servlets;

import taskManager.TaskData;
import taskManager.TasksPool;
import taskManager.tasks.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

/**
 * Created by sergey on 28.03.15.
 */
@WebServlet(name = "NotificationTimeServlet")
public class NotificationTimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/text");
        HttpSession session = request.getSession(true);
        long idUser = (long) session.getAttribute("idUser");
        long idGroup = (long) session.getAttribute("idGroup");
        TaskData data = TasksPool.getInstance().getTask(idUser, idGroup);
        if (data != null) {
            response.getWriter().write("NOTIFICATION!!!!!\n" + "Time: " + data.getDate() + "\n"
                   +"Description: "+ data.getDescription());
        } else {
            response.getWriter().write("");
        }
    }
}
