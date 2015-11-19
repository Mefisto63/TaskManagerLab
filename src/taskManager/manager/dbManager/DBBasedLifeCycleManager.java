package taskManager.manager.dbManager;

import locator.ServiceLocator;
import lockStrategy.LockStrategy;
import taskManager.Group;
import taskManager.TaskData;
import taskManager.User;
import taskManager.connection.TMConnectionPool;
import taskManager.manager.LifeCycleManager;
import taskManager.manager.dbManager.actions.ActionsMap;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by sergey on 14.02.15.
 */
public class DBBasedLifeCycleManager implements LifeCycleManager {
    private TMConnectionPool poolConnection;
    private ActionsMap actionsMap;
    private LockStrategy lockStrategy;

    public DBBasedLifeCycleManager(TMConnectionPool poolConnection, ActionsMap actionsMap, LockStrategy lockStrategy) {
        this.poolConnection = poolConnection;
        this.actionsMap = actionsMap;
        this.lockStrategy = lockStrategy;
    }

    @Override
    public void addTask(Task task, long idUser, List<Long> groupList, List<Long> usersList) throws IOException {
        actionsMap.getAction(task.getType()).insert(task);
        for (Long aLong : usersList) {
            actionsMap.getAction(task.getType()).setTaskInMappingByUser(task.getId(), aLong);
        }
        for (Long aLong : groupList) {
            actionsMap.getAction(task.getType()).setTaskInMappingByGroup(task.getId(), aLong);
        }
        ServiceLocator.getInstance().getLockStrategy().addData(task.getId());
    }

    @Override
    public Set<Task> getTasksOfUser(long idUser) throws IOException {
        Connection connection = poolConnection.get();
        Set<Task> tasks = new LinkedHashSet<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM tasks WHERE tasks.id in (SELECT users_mapping.id_task FROM users_mapping WHERE users_mapping.id_user = ?)")) {
            stm.setLong(1, idUser);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Task task;
                int id_type = resultSet.getInt("id_type");
                task = actionsMap.getAction(id_type).get(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    @Override
    public Task getTask(long id) throws IOException {
        Connection connection = poolConnection.get();
        Task task = null;
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM tasks WHERE tasks.id = ?")) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            int id_type = resultSet.getInt("id_type");
            task = actionsMap.getAction(id_type).get(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    @Override
    public Set<Task> getTasksOfAllGroups(long idUser) throws IOException {
        Connection connection = poolConnection.get();
        Set<Task> tasks = new LinkedHashSet<>();
        try (PreparedStatement stm = connection.prepareStatement
                ("SELECT * FROM tasks WHERE tasks.id in\n" +
                        "           (SELECT groups_mapping.id_task FROM groups_mapping where groups_mapping.id_group in \n" +
                        "                  (with recursive child_group(id, pid) as (\n" +
                        "                   select id, parent_id\n" +
                        "                   from groups\n" +
                        "                   where id in (SELECT users.id_group FROM users WHERE users.id = ?) \n" +
                        "                   union all\n" +
                        "                   select C.id, C.parent_id\n" +
                        "                   from child_group P\n" +
                        "                   inner join groups C on P.id = C.parent_id\n" +
                        "                   )\n" +
                        "                   select id from child_group));")) {
            stm.setLong(1, idUser);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Task task;
                int id_type = resultSet.getInt("id_type");
                task = actionsMap.getAction(id_type).get(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) throws IOException {

        actionsMap.getAction(task.getType()).update(task);
        lockStrategy.unlockData(task.getId());
    }

    @Override
    public void deleteTask(long id) throws IOException {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM triggers WHERE triggers.id_task = ?")) {
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM users_mapping WHERE users_mapping.id_task = ?")) {
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM groups_mapping WHERE groups_mapping.id_task = ?")) {
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM tasks WHERE tasks.id = ?")) {
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Соединение не закрыто");
            }
        }
    }

    @Override
    public void addTrigger(Trigger trigger) throws IOException {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO triggers VALUES(?,?,?)")) {
            stm.setLong(1, trigger.getId());
            stm.setTimestamp(2, new Timestamp(trigger.getDate().getTime()));
            stm.setLong(3, trigger.getIdTask());
            stm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Trigger> getAllTriggers() throws IOException {
        Connection connection = poolConnection.get();
        ArrayList<Trigger> triggers = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM triggers")) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long idTask = resultSet.getLong("id_task");
                Date date = new Date(resultSet.getTimestamp("date_trig").getTime());
                triggers.add(new Trigger(date, idTask, id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return triggers;
    }

    @Override
    public ArrayList<Trigger> getTrigger(long id) throws IOException {
        Connection connection = poolConnection.get();
        ArrayList<Trigger> triggers = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM triggers WHERE triggers.id = ?")) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                long idTask = resultSet.getLong("id_task");
                Date date = new Date(resultSet.getTimestamp("date_trig").getTime());
                triggers.add(new Trigger(date, idTask, id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return triggers;
    }

    @Override
    public void updateTrigger(Trigger trigger) throws IOException {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("UPDATE triggers SET  date_trig = ?, id_task = ? WHERE id = ?")) {
            stm.setTimestamp(1, new Timestamp(trigger.getDate().getTime()));
            stm.setLong(2, trigger.getIdTask());
            stm.setLong(3, trigger.getId());
            stm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTrigger(long id) throws IOException {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM triggers WHERE triggers.id = ?")) {
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Date getNearestTime() throws IOException {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("SELECT MIN (triggers.date_trig) FROM triggers")) {
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            Timestamp timestamp = resultSet.getTimestamp(1);
            Date date = new Date(timestamp.getTime());
            return date;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User authorization(String login, String pass) throws IOException {
        Connection connection = poolConnection.get();
        long id;
        try (PreparedStatement stm = connection.prepareStatement("SELECT users.id,users.name_log, users.pass, users.id_group FROM users WHERE users.name_log = ? AND users.pass = ?")) {
            stm.setString(1, login);
            stm.setString(2, pass);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
                User user = new User(id, login, resultSet.getLong("id_group"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean registration(String login, String pass, long idGroup) {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO users (name_log, pass, id_group) VALUES (?,?,?)")) {
            stm.setString(1, login);
            stm.setString(2, pass);
            stm.setLong(3, idGroup);
            stm.execute();
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Map<Long, String> getGroups(long id) {
        Map<Long, String> map = new HashMap<>();
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("with recursive child_group(id, name, pid) as (\n" +
                        "  select id, group_name, parent_id\n" +
                        "  from groups\n" +
                        "  where id in (SELECT users.id_group FROM users WHERE users.id = ?)\n" +
                        "  union all\n" +
                        "  select C.id, C.group_name, C.parent_id\n" +
                        "  from child_group P\n" +
                        "    inner join groups C on P.id = C.parent_id\n" +
                        ")\n" +
                        "select * from child_group;")) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            while (rs.next()) {
                map.put(rs.getLong(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении групп");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public Map<Long, String> getChildGroups(long idUser) {
        Map<Long, String> map = new HashMap<>();
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("with recursive child_group(id, pid, gr_name) as (\n" +
                        "  select id, parent_id, group_name\n" +
                        "  from groups\n" +
                        "  where id in (SELECT users.id_group FROM users WHERE users.id = ?)\n" +
                        "  union all\n" +
                        "  select C.id, C.parent_id, C.group_name\n" +
                        "  from child_group P\n" +
                        "    inner join groups C on P.id = C.parent_id\n" +
                        ")\n" +
                        "select id, gr_name from child_group;")) {
            stm.setLong(1, idUser);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                map.put(rs.getLong(1), rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении групп");
            System.out.println(idUser);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public int countGroups() {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("SELECT count(*) FROM groups")) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int countUsers() {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("SELECT count(*) FROM users")) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public long getVersion(long id) {
        long version = 0;
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("SELECT tasks.ver FROM tasks WHERE tasks.id = ?")) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            version = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return version;
    }

    @Override
    public Group getGroup(long idUser) {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("SELECT id,group_name,parent_id FROM groups WHERE groups.id IN " +
                "(SELECT users.id_group FROM users WHERE users.id = ?)")) {
            stm.setLong(1, idUser);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Group group = new Group(rs.getLong(1), rs.getString(2), rs.getLong(3));
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Task> getTasksOfCurGroup(long id) {
        Connection connection = poolConnection.get();
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM tasks WHERE tasks.id in " +
                "(SELECT groups_mapping.id_task FROM groups_mapping WHERE groups_mapping.id_group = ?) ")) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Task task;
                int id_type = resultSet.getInt("id_type");
                task = actionsMap.getAction(id_type).get(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    @Override
    public List<Group> getChildGroup(long id) {
        Connection connection = poolConnection.get();
        List<Group> groups = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT id,group_name,parent_id FROM groups WHERE parent_id = ?")) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }

    @Override
    public List<User> getUsersInGroup(long idGroup) {
        Connection connection = poolConnection.get();
        List<User> users = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT users.id, users.name_log, users.id_group FROM users WHERE id_group = ?")) {
            stm.setLong(1, idGroup);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public int getLevel(long idGroup) {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("with recursive child_group(id, pid, level) as (\n" +
                "  select id, parent_id, 1\n" +
                "  from groups\n" +
                "  where id = ?\n" +
                "  union all\n" +
                "  select C.id, C.parent_id, P.level + 1\n" +
                "  from child_group P\n" +
                "    inner join groups C on P.id = C.parent_id\n" +
                ")\n" +
                "select count(DISTINCT level) from child_group")) {
            stm.setLong(1, idGroup);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void createGroup(long pid, String name) {
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO groups(group_name, parent_id) VALUES(?,?)")) {
            stm.setString(1, name);
            stm.setLong(2, pid);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<Long, String> getUsers(long id) {
        Map<Long, String> map = new HashMap<>();
        Connection connection = poolConnection.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("SELECT users.name_log, users.id FROM users WHERE users.id_group in " +
                        "(with recursive child_group(id, name, pid) as (\n" +
                        "  select id, group_name, parent_id\n" +
                        "  from groups\n" +
                        "  where id in (SELECT users.id_group FROM users WHERE users.id = ?)\n" +
                        "  union all\n" +
                        "  select C.id, C.group_name, C.parent_id\n" +
                        "  from child_group P\n" +
                        "    inner join groups C on P.id = C.parent_id\n" +
                        ")\n" +
                        "select id from child_group);")) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                map.put(rs.getLong(2), rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при получении групп");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public List<TaskData> getTasksData() {
        Connection connection = poolConnection.get();
        List<TaskData> dataList = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT tasks.id, tasks.description, tasks.id_type, triggers.id AS id_trigger, triggers.date_trig, users_mapping.id_user\n" +
                "FROM (triggers INNER JOIN tasks ON triggers.id_task = tasks.id)\n" +
                "INNER JOIN users_mapping ON tasks.id = users_mapping.id_task;")) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                long idUser = resultSet.getLong("id_user");
                long idTask = resultSet.getLong("id");
                long idTrigger = resultSet.getLong("id_trigger");
                int type = resultSet.getInt("id_type");
                Date date = new Date(resultSet.getTimestamp("date_trig").getTime());
                String desc = resultSet.getString("description");
                TaskData data = new TaskData(date, desc, "", idTask, idTrigger);
                data.setIdUser(idUser);
                data.setType(type);
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT tasks.id, tasks.description, tasks.id_type, triggers.id AS id_trigger, triggers.date_trig, groups_mapping.id_group\n" +
                "FROM (triggers INNER JOIN tasks ON triggers.id_task = tasks.id)\n" +
                "  INNER JOIN groups_mapping ON tasks.id = groups_mapping.id_task;")) {
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                long idGroup = resultSet.getLong("id_group");
                long idTask = resultSet.getLong("id");
                long idTrigger = resultSet.getLong("id_trigger");
                int type = resultSet.getInt("id_type");
                Date date = new Date(resultSet.getTimestamp("date_trig").getTime());
                String desc = resultSet.getString("description");
                TaskData data = new TaskData(date, desc, "", idTask, idTrigger);
                data.setIdGroup(idGroup);
                data.setType(type);
                dataList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dataList;
    }

}


/*
"SELECT * FROM tasks WHERE tasks.id in \n" +
                        "     (SELECT users_mapping.id_task FROM users_mapping WHERE users_mapping.id_user in \n" +
                        "           (SELECT users.id FROM users where users.id_group in \n" +
                        "                  (WITH recursive child_group(id, pid) AS (\n" +
                        "                   SELECT id, parent_id\n" +
                        "                   FROM groups\n" +
                        "                   WHERE id in (SELECT users.id_group FROM users WHERE users.id = ?)\n" +
                        "                   UNION All\n" +
                        "                   SELECT C.id, C.parent_id\n" +
                        "                   FROM child_group P\n" +
                        "                   INNER JOIN groups C ON P.id = C.parent_id\n" +
                        "                   )\n" +
                        "                   SELECT id FROM child_group)));"
 */