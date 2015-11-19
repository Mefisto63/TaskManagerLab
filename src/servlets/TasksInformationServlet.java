package servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sergey on 01.03.15.
 */
@WebServlet(name = "TasksInformationServlet")
public class TasksInformationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idUser;
//        if (request.getAttribute("idUser") != null)
//            idUser = (long) request.getAttribute("idUser");
//        else
//            idUser = Long.parseLong(request.getParameter("idUser"));
        HttpSession session = request.getSession(true);
        idUser = (long) session.getAttribute("idUser");
        String err = "";
        err = request.getParameter("err");
        //err += "";
        if (err == null) {
            response.sendRedirect("/myApp/tasksPage.jsp");
        } else {
            response.sendRedirect("/myApp/tasksPage.jsp?err=" + err);
        }
    }
}
