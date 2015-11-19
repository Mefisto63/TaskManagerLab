package taskManager;

import taskManager.requests.RequestProcessorsMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sergey on 13.12.14.
 */
public class Server {
    public static ServerSocket serverSocket = null;
    public static int port = 2238;
    private RequestProcessorsMap processorsMap;

    public Server(RequestProcessorsMap processorsMap) {
        this.processorsMap = processorsMap;
    }

    public void start() {

//        IdGenerator idGenerator;
//        switch (args[0]){
//            case "TimeGenerator":
//                idGenerator = new TimeIdGenerator();
//                break;
//            default:throw new RuntimeException("No id generator was found");
//        }
//
//        TMPoolConnection tmPoolConnection = new TMPoolConnection();
//        for (int i = 0; i < 5; i++){
//            Class.forName("org.postgresql.Driver");
//            Connection connection = DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/TaskManagerData", "postgres", "963645987");
//            TMConnection tmConnection = new TMConnection(connection, tmPoolConnection);
//            tmPoolConnection.putTMConnection(tmConnection);
//        }
//
//        TaskLog taskLog = new TaskLog("Out.xml");
//        TriggerLog triggerLog = new TriggerLog();
//        //ConnectionToDB connectionToDB = new ConnectionToDB("TaskManagerData", "postgres", "963645987");
//
//        Map<Integer, TaskActions> actions = new HashMap<>();
//        actions.put(1, new NotificationActions());
//        actions.put(2, new AlarmClockActions());
//        actions.put(3, new BirthdayReminderActions());
//        ActionsMap actionsMap = new ActionsMap(actions);
//        LifeCycleManager lifeCycleManager = new DBBasedLifeCycleManager(tmPoolConnection, actionsMap);
//
//        Map<Integer,Factory> factoryMap = new HashMap<>();
//        factoryMap.put(Notification.TYPE, new GenericNotificationFactory(idGenerator));
//        factoryMap.put(BirthdayReminder.TYPE, new GenericBirthdayFactory(idGenerator));
//        factoryMap.put(AlarmClock.TYPE, new GenericAlarmClockFactory(idGenerator));
//        factoryMap.put(4, new GenericTriggerFactory());
//
//        Factories factories = new Factories(factoryMap);
//        Controller controller = new Controller(factories, idGenerator, lifeCycleManager);
//
//        Map<Integer, RequestProcessor> processorMap = new LinkedHashMap<>();
//        processorMap.put(4, new CreateTaskProcessor(controller));
//        processorMap.put(5, new DeleteTaskProcessor(controller));
//        processorMap.put(9, new GetDataProcessor(controller));
//        processorMap.put(10, new LoginRequestProcessor());
//        processorMap.put(11, new RegisterClientProcessor());
//        processorMap.put(12, new CreateTriggerProcessor(controller));
//        processorMap.put(13, new UpdateDataProcessor(controller));
//
//
//        RequestProcessorsMap processorsMap = new RequestProcessorsMap(processorMap);


        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Сокет занят");
        }
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Возникла ошибка");
            }
            System.out.println("Got client");

//            System.out.println(socket.getInetAddress() + "   " + socket.getPort());
            RequestThread thread = new RequestThread(new Transport(socket), processorsMap);
//            thread.setDaemon(true);
            thread.start();


        }



    }
}
