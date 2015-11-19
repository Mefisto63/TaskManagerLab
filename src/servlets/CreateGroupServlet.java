package servlets;

import locator.ServiceLocator;
import taskManager.manager.LifeCycleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 22.03.15.
 */
@WebServlet(name = "CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        long idGroup = Long.parseLong(request.getParameter("groups"));
        LifeCycleManager lcm = ServiceLocator.getInstance().getLifeCycleManager();
        lcm.createGroup(idGroup, name);
        response.sendRedirect("/myApp/tasksPage.jsp");
    }
}
