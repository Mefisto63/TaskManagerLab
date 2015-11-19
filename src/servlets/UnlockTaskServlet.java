package servlets;

import locator.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 18.03.15.
 */
@WebServlet(name = "UnlockTaskServlet")
public class UnlockTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id =Long.parseLong(request.getParameter("id"));
        ServiceLocator.getInstance().getLockStrategy().unlockData(id);
        response.sendRedirect("/myApp/task?id="+id);
    }
}
