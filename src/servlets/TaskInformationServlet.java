package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;
import taskManager.tasks.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 01.03.15.
 */
@WebServlet(name = "TaskInformationServlet")
public class TaskInformationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("id"));
        Controller controller = ServiceLocator.getInstance().getController();
        Task task = controller.getTask(id);
        request.setAttribute("ver", ServiceLocator.getInstance().getLifeCycleManager().getVersion(id));
        request.setAttribute("id", task.getId());
        request.setAttribute("name", task.getName());
        request.setAttribute("desc", task.getDescription());
        request.setAttribute("type", task.getType());
        request.setAttribute("task", task);
        request.setAttribute("triggers", controller.getWorkingTasks());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/taskInformationPage.jsp?id=" + id);
        dispatcher.forward(request, response);

    }
}
