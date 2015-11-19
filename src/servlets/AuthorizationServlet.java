package servlets;

import locator.ServiceLocator;
import taskManager.User;
import taskManager.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sergey on 28.02.15.
 */
@WebServlet(name = "AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        Controller controller = ServiceLocator.getInstance().getController();

        User user = controller.authorization(login, pass);
        if (user == null){
            response.sendRedirect("/myApp/login.jsp");
        } else {
            request.setAttribute("idUser", user);
//            response.sendRedirect("/myApp/tasksPage.jsp");
            HttpSession session = request.getSession(true);
            session.setAttribute("idUser", user.getId());
            session.setAttribute("NameUser", login);
            session.setAttribute("idGroup", user.getIdGroup());
            request.getRequestDispatcher("/tasks").forward(request, response);
        }

    }
}
