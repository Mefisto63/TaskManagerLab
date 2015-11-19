package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sergey on 11.03.15.
 */
@WebServlet(name = "CreateTriggerServlet")
public class CreateTriggerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        String str = request.getParameter("date") + " " + request.getParameter("time");
        long id = Long.parseLong(request.getParameter("id"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
            controller.createTrigger(date, id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/myApp/task?id=" + id);

    }
}
