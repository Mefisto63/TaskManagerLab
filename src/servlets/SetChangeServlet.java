package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;
import taskManager.requests.factory.data.AlarmTaskData;
import taskManager.requests.factory.data.BirthdayTaskData;
import taskManager.requests.factory.data.Data;
import taskManager.requests.factory.data.NotificationTaskData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 08.03.15.
 */
@WebServlet(name = "SetChangeServlet")
public class SetChangeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Controller controller = ServiceLocator.getInstance().getController();
        long id = Long.parseLong(request.getParameter("id"));
        long version = Long.parseLong(request.getParameter("ver"));
        int type = Integer.parseInt(request.getParameter("type"));
        String desc = request.getParameter("desc");
        String countRepeatStr = request.getParameter("countRepeat");
        String intervalStr = request.getParameter("interval");
        int countRepeat = 0, interval = 0;
        if (countRepeatStr != null){
            countRepeat = Integer.parseInt(countRepeatStr);
            interval = Integer.parseInt(intervalStr);
        }
        Data data = null;
        switch (type){
            case 1:
                data = new NotificationTaskData(null, 0, id, desc);
                break;
            case 2:
                data = new AlarmTaskData(null,0,id,0,desc,countRepeat,interval);
                break;
            case 3:
                data = new BirthdayTaskData(null,0, id,0, desc);
                break;
        }
        try {
            ServiceLocator.getInstance().getLockStrategy().checkVersion(id, version);
            controller.update(data);
            response.sendRedirect("/myApp/tasks");
        } catch (IOException e) {
            try {
                response.sendRedirect("/myApp/tasks?err=" + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
