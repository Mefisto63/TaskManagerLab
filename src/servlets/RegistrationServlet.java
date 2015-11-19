package servlets;

import beans.CreateUserEJB;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 07.03.15.
 */
@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    @EJB
    private CreateUserEJB createUserBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("login");
        String pass = request.getParameter("pass");
        String str = request.getParameter("groups");
        if (str != null) {
            long idGroup = Long.parseLong(str);
            if (createUserBean.createUser(name, pass, idGroup)){
                response.sendRedirect("/myApp/tasksPage.jsp");
            } else {
                String error = "This name is already exists";
                response.sendRedirect("/myApp/registration.jsp?text=" + error);
            }
//            Controller controller = ServiceLocator.getInstance().getController();
//            if (controller.registration(name, pass, idGroup)) {
//                String text = "Registration was successful";
//                response.sendRedirect("/myApp/tasksPage.jsp");
//            } else {
//                String error = "This name is already exists";
//                response.sendRedirect("/myApp/registration.jsp?text=" + error);
//            }
        } else {
            response.sendRedirect("/myApp/tasksPage.jsp?err=" + "you cant create user");
        }

    }
}
