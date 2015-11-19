package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sergey on 07.03.15.
 */
@WebServlet(name = "DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        long id = Long.parseLong(request.getParameter("id"));
        long version = Long.parseLong(request.getParameter("ver"));
        Controller controller = ServiceLocator.getInstance().getController();
        HttpSession session = request.getSession(true);
        long idUser = (long) session.getAttribute("idUser");
        try {
            ServiceLocator.getInstance().getLockStrategy().checkBlock(id);
            ServiceLocator.getInstance().getLockStrategy().checkVersion(id, version);
            controller.deleteTask(id);
            response.sendRedirect("/myApp/tasksPage.jsp");
        } catch (IOException e){
            try {
                response.sendRedirect("/myApp/tasks?err=" + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
