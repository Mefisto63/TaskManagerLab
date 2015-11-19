package taskManager;

import taskManager.connection.TMConnection;
import taskManager.connection.TMConnectionPool;
import taskManager.controller.Controller;
import taskManager.idGenerator.IdGenerator;
import taskManager.idGenerator.TimeIdGenerator;
import taskManager.manager.dbManager.actions.*;
import taskManager.requests.RequestProcessorsMap;
import taskManager.requests.factory.Factories;
import taskManager.requests.factory.genericFactory.*;
import taskManager.requests.processors.*;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.BirthdayReminder;
import taskManager.tasks.Notification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sergey on 21.02.15.
 */
public class Main {
    private static Controller controller;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        IdGenerator idGenerator;
        switch (args[0]){
            case "TimeGenerator":
                idGenerator = new TimeIdGenerator();
                break;
            default:throw new RuntimeException("No id generator was found");
        }

        TMConnectionPool tmConnectionPool = new TMConnectionPool();
        for (int i = 0; i < 5; i++){
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/TaskManagerData", "postgres", "963645987");
            TMConnection tmConnection = new TMConnection(connection, tmConnectionPool);
            tmConnectionPool.putTMConnection(tmConnection);
        }

        //TaskLog taskLog = new TaskLog("Out.xml");
        //TriggerLog triggerLog = new TriggerLog();
        //ConnectionToDB connectionToDB = new ConnectionToDB("TaskManagerData", "postgres", "963645987");

        Map<Integer, TaskActions> actions = new HashMap<>();
        actions.put(1, new NotificationActions(tmConnectionPool));
        actions.put(2, new AlarmClockActions(tmConnectionPool));
        actions.put(3, new BirthdayReminderActions(tmConnectionPool));
        ActionsMap actionsMap = new ActionsMap(actions);
        //LifeCycleManager lifeCycleManager = new DBBasedLifeCycleManager(tmConnectionPool, actionsMap);

        Map<Integer,Factory> factoryMap = new HashMap<>();
        factoryMap.put(Notification.TYPE, new GenericNotificationFactory(idGenerator));
        factoryMap.put(BirthdayReminder.TYPE, new GenericBirthdayFactory(idGenerator));
        factoryMap.put(AlarmClock.TYPE, new GenericAlarmClockFactory(idGenerator));
        factoryMap.put(4, new GenericTriggerFactory());

        Factories factories = new Factories(factoryMap);
        //controller = new Controller(factories, idGenerator, lifeCycleManager);

        Map<Integer, RequestProcessor> processorMap = new LinkedHashMap<>();
        processorMap.put(4, new CreateTaskProcessor(controller));
        processorMap.put(5, new DeleteTaskProcessor(controller));
        processorMap.put(9, new GetDataProcessor(controller));
        processorMap.put(10, new LoginRequestProcessor());
        processorMap.put(11, new RegisterClientProcessor());
        processorMap.put(12, new CreateTriggerProcessor(controller));
        processorMap.put(13, new UpdateDataProcessor(controller));

        RequestProcessorsMap processorsMap = new RequestProcessorsMap(processorMap);
        processorMap.put(15, new CombinedRequestProcessor(processorsMap, controller));

        Server server = new Server(processorsMap);
        server.start();

    }

    public static Controller getController() {
        return controller;
    }
}
