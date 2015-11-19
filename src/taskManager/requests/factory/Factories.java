package taskManager.requests.factory;

import taskManager.requests.factory.genericFactory.Factory;
import taskManager.requests.factory.genericFactory.GenericAlarmClockFactory;
import taskManager.requests.factory.genericFactory.GenericBirthdayFactory;
import taskManager.requests.factory.genericFactory.GenericNotificationFactory;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.BirthdayReminder;
import taskManager.tasks.Notification;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sergey on 01.12.14.
 */
public class Factories {
    private Map<Integer, Factory> factoryMap;

    public Factories(Map<Integer,Factory> factoryMap) {
        this.factoryMap = factoryMap;

    }

    public Factory getFactory(int type) {
        return factoryMap.get(type);
    }

//    public void addFactory(int type, Factory factory){
//        factoryMap.put(type, factory);
//    }
}
