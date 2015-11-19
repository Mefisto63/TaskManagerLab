package listener; /**
 * Created by sergey on 04.03.15.
 */

import locator.ServiceLocator;
import locator.TestLocator;
import lockStrategy.LockStrategy;
import lockStrategy.optimistic.OptimisticLock;
import taskManager.connection.TMConnection;
import taskManager.connection.TMConnectionPool;
import taskManager.controller.Controller;
import taskManager.idGenerator.DBIdGenerator;
import taskManager.idGenerator.IdGenerator;
import taskManager.manager.LifeCycleManager;
import taskManager.manager.dbManager.DBBasedLifeCycleManager;
import taskManager.manager.dbManager.actions.*;
import taskManager.requests.RequestProcessorsMap;
import taskManager.requests.factory.Factories;
import taskManager.requests.factory.genericFactory.*;
import taskManager.requests.processors.RequestProcessor;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.BirthdayReminder;
import taskManager.tasks.Notification;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StarUpListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public StarUpListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        Context initialContext = null;
        DataSource datasource = null;
        TMConnectionPool tmConnectionPool = new TMConnectionPool();
        try {
            initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("java:/DSTaskManager");
            for (int i = 0; i < 15; i++) {
                Connection tmConnection = new TMConnection(datasource.getConnection(), tmConnectionPool);
                tmConnectionPool.putTMConnection(tmConnection);
            }
            System.out.println(datasource.getConnection().toString());
        } catch (NamingException e) {
            System.out.println("Не удалось получить DataSource");
        } catch (SQLException e) {
            System.out.println("Не удалось получить connection");
        }

        LockStrategy lockStrategy = new OptimisticLock(tmConnectionPool.get());

        IdGenerator idGenerator = new DBIdGenerator(tmConnectionPool);
        Map<Integer, TaskActions> actions = new HashMap<>();
        actions.put(1, new NotificationActions(tmConnectionPool));
        actions.put(2, new AlarmClockActions(tmConnectionPool));
        actions.put(3, new BirthdayReminderActions(tmConnectionPool));
        ActionsMap actionsMap = new ActionsMap(actions);

        LifeCycleManager lifeCycleManager = new DBBasedLifeCycleManager(tmConnectionPool, actionsMap, lockStrategy);

        Map<Integer, Factory> factoryMap = new HashMap<>();
        factoryMap.put(Notification.TYPE, new GenericNotificationFactory(idGenerator));
        factoryMap.put(BirthdayReminder.TYPE, new GenericBirthdayFactory(idGenerator));
        factoryMap.put(AlarmClock.TYPE, new GenericAlarmClockFactory(idGenerator));
        factoryMap.put(4, new GenericTriggerFactory());

        Factories factories = new Factories(factoryMap);
        Controller controller = null;
        try {
            controller = new Controller(factories, idGenerator, lifeCycleManager);
        } catch (IOException e) {
            System.out.println("Возникла ошибка при создании контроллера");
        }

        Map<Integer, RequestProcessor> processorMap = new LinkedHashMap<>();

        RequestProcessorsMap processorsMap = new RequestProcessorsMap(processorMap);

        ServiceLocator serviceLocator = new TestLocator(controller, processorsMap, lifeCycleManager, lockStrategy);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
