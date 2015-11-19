package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;
import taskManager.manager.LifeCycleManager;
import taskManager.requests.factory.data.AlarmTaskData;
import taskManager.requests.factory.data.BirthdayTaskData;
import taskManager.requests.factory.data.Data;
import taskManager.requests.factory.data.NotificationTaskData;
import taskManager.tasks.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sergey on 01.03.15.
 */
@WebServlet(name = "CreateTaskServlet")
public class CreateTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controller controller = ServiceLocator.getInstance().getController();
        LifeCycleManager lcm = ServiceLocator.getInstance().getLifeCycleManager();
        int type = 0;
        List<Long> groupList = new ArrayList<>();
        for (int i = 0; i <= controller.countGroups(); i++){
            String str = request.getParameter(i + "_group");
            if (str != null)
                groupList.add(Long.parseLong(str));
        }
        List<Long> usersList = new ArrayList<>();
        for (int i = 0; i <= lcm.countUsers(); i++){
            String str = request.getParameter(i + "_user");
            if (str != null)
                usersList.add(Long.parseLong(str));
        }
        if (request.getParameter("type") != null)
            type = Integer.parseInt(request.getParameter("type"));
        String desc = request.getParameter("desc");
        HttpSession session = request.getSession(true);
        long idUser = (long) session.getAttribute("idUser");
        String str = request.getParameter("date") + " " + request.getParameter("time");
        String countRepeatStr = request.getParameter("countRepeat");
        String intervalStr = request.getParameter("interval");
        int countRepeat = 0, interval = 0;
        if (countRepeatStr != null){
            countRepeat = Integer.parseInt(countRepeatStr);
            interval = Integer.parseInt(intervalStr);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
            Task task = null;
            Data data = null;
            switch (type){
                case 1:
                    data = new NotificationTaskData(desc, date, idUser);
                    break;
                case 2:
                    data = new AlarmTaskData(desc, date, countRepeat, interval, idUser);
                    break;
                case 3:
                    data = new BirthdayTaskData(desc, date, idUser);
                    break;
            }
            task = controller.createTask(data, groupList, usersList);
            if (task != null) {
                controller.createTrigger(date, task.getId());
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        response.sendRedirect("/myApp/tasksPage.jsp");
    }
}
