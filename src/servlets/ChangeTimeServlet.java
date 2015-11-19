package servlets;

import locator.ServiceLocator;
import taskManager.controller.Controller;
import taskManager.requests.factory.data.Data;
import taskManager.requests.factory.data.TriggerData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sergey on 10.03.15.
 */
@WebServlet(name = "ChangeTimeServlet")
public class ChangeTimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> list = Collections.list(parameterNames);
        list.remove(0);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                int i1 = Integer.parseInt(a.substring(0, a.indexOf("_")));
                int i2 = Integer.parseInt(b.substring(0, b.indexOf("_")));
                return Integer.valueOf(i1).compareTo(Integer.valueOf(i2));
            }
        });

        int k = 0;
        String str = "";
        long id = 0;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String paramNames = (String) iterator.next();
            if (!paramNames.equals("idTask")) {
                if (k == 1) {
                    str = request.getParameter(paramNames);
                    paramNames = (String) iterator.next();
                    str += " " + request.getParameter(paramNames);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Controller controller = ServiceLocator.getInstance().getController();
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(str);
                        Data data = new TriggerData(id, date);
                        controller.update(data);
                    } catch (ParseException e) {
                        System.out.println("Unparseable date");
                    }
                    k = 0;
                    str = "";
                } else {
                    id = Long.parseLong(request.getParameter(paramNames));
                    k++;
                }
            }
        }
        response.sendRedirect("/myApp/task?id=" + request.getParameter("idTask"));
    }
}
