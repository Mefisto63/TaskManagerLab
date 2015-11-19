package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 08.03.15.
 */
@WebServlet(name = "UpdatePageServlet")
public class UpdatePageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        timeoutThread(id);
        Controller controller = ServiceLocator.getInstance().getController();
        try {
            Task task = controller.getTask(id);
            ServiceLocator.getInstance().getLockStrategy().checkBlock(id);
            request.setAttribute("version", Long.parseLong(request.getParameter("ver")));
            request.setAttribute("id", id);
            request.setAttribute("desc", task.getDescription());
            request.setAttribute("type", task.getType());
            if (task.getType() == 2) {
                AlarmClock clock = (AlarmClock) task;
                request.setAttribute("repeat", clock.getRepeatCount());
                request.setAttribute("interval", clock.getInterval());
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/update.jsp?id=" + id);
            dispatcher.forward(request, response);
        } catch (IOException e) {
            try {
                response.sendRedirect("/myApp/tasks?err=" + e.getMessage());
                System.out.println(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void timeoutThread(final long id){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    ServiceLocator.getInstance().getLockStrategy().checkBlock(id);
                } catch (IOException e) {
                    ServiceLocator.getInstance().getLockStrategy().unlockData(id);
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
