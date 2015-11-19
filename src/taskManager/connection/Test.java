package taskManager.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by sergey on 26.02.15.
 */
public class Test {
    public static void main (String[] args) throws ClassNotFoundException, SQLException, IOException {
        final TMConnectionPool connectionPool=new TMConnectionPool();
        final Connection connection2 = new TMConnection(null,connectionPool);
        Class.forName("org.postgresql.Driver");
        final Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TaskManagerData", "postgres", "963645987");
        final Connection connection1 = new TMConnection(connection,connectionPool);
        Connection tmConnection = new TMConnection(connection, connectionPool);
        connectionPool.putTMConnection(connection1);


//        PreparedStatement st = tmConnection.prepareStatement("SELECT * FROM users");
//        ResultSet rs = st.executeQuery();
//        rs.next();

//        ~
//
//        Thread t1 = new Thread(runnable);
//        Thread t2 = new Thread(runnable);
//        Thread t3 = new Thread(runnable);
//        Thread t4 = new Thread(runnable);
//        Thread t5 = new Thread(runnable);

//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();
//        for (int i = 0; i < 5; i++){
//            Thread t = new Thread(runnable);
//            t.start();
//        }
//
//        RequestProcessorsMap map = new RequestProcessorsMap(null);
//        Controller controller = null;
//        ServiceLocator serviceLocator = new TestLocator(controller, map);
//        ServiceLocator.getInstance().getController();
//        RequestProcessorsMap processorsMap = ServiceLocator.getInstance().getProcessorsMap();
//        ServiceLocator serviceLocator1 = new TestLocator(controller, map);
//        ServiceLocator asd = ServiceLocator.getInstance();

//        IdGenerator idGenerator = new TimeIdGenerator();
//        TMConnectionPool tmConnectionPool = new TMConnectionPool();
//        for (int i = 0; i < 5; i++) {
//            try {
//                Class.forName("org.postgresql.Driver");
//                Connection connection = DriverManager.getConnection(
//                        "jdbc:postgresql://localhost:5432/TaskManagerData", "postgres", "963645987");
//                Connection tmConnection = new TMConnection(connection, tmConnectionPool);
//                tmConnectionPool.putTMConnection(tmConnection);
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        Map<Integer, TaskActions> actions = new HashMap<>();
//        actions.put(1, new NotificationActions(tmConnectionPool));
//        actions.put(2, new AlarmClockActions(tmConnectionPool));
//        actions.put(3, new BirthdayReminderActions(tmConnectionPool));
//        ActionsMap actionsMap = new ActionsMap(actions);
//
//        LifeCycleManager lifeCycleManager = new DBBasedLifeCycleManager(tmConnectionPool, actionsMap);
//
//        Map<Integer, Factory> factoryMap = new HashMap<>();
//        factoryMap.put(Notification.TYPE, new GenericNotificationFactory(idGenerator));
//        factoryMap.put(BirthdayReminder.TYPE, new GenericBirthdayFactory(idGenerator));
//        factoryMap.put(AlarmClock.TYPE, new GenericAlarmClockFactory(idGenerator));
//        factoryMap.put(4, new GenericTriggerFactory());
//
//        Factories factories = new Factories(factoryMap);
//        Controller controller = null;
//        try {
//            controller = new Controller(factories, idGenerator, lifeCycleManager);
//            controller.registration("kigo","asdasd");
//        } catch (IOException e) {
//            System.out.println("Возникла ошибка при создании контроллера");
//        }
//        Connection connection = tmConnectionPool.get();
//        try (PreparedStatement stm = connection.prepareCall("SELECT return_ids(?)")){
//            stm.setInt(1, 2);
//            ResultSet set = stm.executeQuery();
//            set.next();
//            Array array = set.getArray(1);
//            Object[] str = (Object[]) array.getArray();
//            ArrayList<Long> arrayList = new ArrayList<>();
//            for (Object o : str) {
//                arrayList.add(Long.parseLong(o.toString()));
//            }
//            for (Long aLong : arrayList) {
//                System.out.println(aLong);
//            }
//
//
//        } finally {
//            connection.close();
//        }
//        PreparedStatement statement = connection.prepareStatement(
//                "with recursive child_group(id, pid) as (\n" +
//                "  select id, parent_id\n" +
//                "  from groups\n" +
//                "  where id = ?\n" +
//                "  union all\n" +
//                "  select C.id, C.parent_id\n" +
//                "  from child_group P\n" +
//                "    inner join groups C on P.id = C.parent_id\n" +
//                ")\n" +
//                "select id from child_group;");
//        statement.setLong(1, 3);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()){
//            System.out.println(rs.getLong(1));
//        }
//        PreparedStatement statement = connection.prepareStatement("SELECT users.assign_task FROM users WHERE users.id = 20");
//        ResultSet rs = statement.executeQuery();
//        rs.next();
//        Array array = rs.getArray(1);
//        Long[] longs = (Long[]) array.getArray();
//        ArrayList<Long> list = new ArrayList<>(Arrays.asList(longs));
//        list.add((long) 534);
//        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET assign_task =? WHERE id = 20");
//        Array array1 = connection.createArrayOf("bigint", list.toArray());
//        statement1.setArray(1, array1);
//        statement1.execute();
//        LockStrategy ls = new PessimisticLock(connectionPool);
//        ls.checkBlock(101);
//        ls.checkBlock(101);
//        connection.close();
    }
}
