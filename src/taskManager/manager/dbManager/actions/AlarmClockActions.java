package taskManager.manager.dbManager.actions;

import taskManager.connection.TMConnectionPool;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergey on 18.02.15.
 */
public class AlarmClockActions implements TaskActions<AlarmClock> {
    private TMConnectionPool connectionPool;

    public AlarmClockActions(TMConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Task get(ResultSet resultSet) throws SQLException {
        Task task = null;
        String desc = resultSet.getString("description");
        int interval = resultSet.getInt("interval_rep");
        int repeatCount = resultSet.getInt("repeat_count");
        int curRepeat = resultSet.getInt("cur_repeat");
        long id = resultSet.getLong("id");
        AlarmClock alarmClock = new AlarmClock(desc, repeatCount, id, interval);
        alarmClock.setCurrentRepeat(curRepeat);
        task = alarmClock;
        return task;
    }

    @Override
    public void insert(AlarmClock task) throws IOException {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO tasks VALUES(?,?,?,?,?,?)")) {
            stm.setLong(1, task.getId());
            stm.setString(2, task.getDescription());
            stm.setInt(6, task.getType());
            stm.setInt(3, task.getInterval());
            stm.setInt(4, task.getRepeatCount());
            stm.setInt(5, task.getCurrentRepeat());
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
    public void update(AlarmClock task) {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("UPDATE tasks SET description = ?, interval_rep = ?, repeat_count = ?, cur_repeat =? WHERE tasks.id = ?")) {
            stm.setLong(5, task.getId());
            stm.setString(1, task.getDescription());
            stm.setInt(2, task.getInterval());
            stm.setInt(3, task.getRepeatCount());
            stm.setInt(4, task.getCurrentRepeat());
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
    public void setTaskInMappingByUser(long idTask, long idUser) {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO users_mapping(id_task, id_user) VALUES(?,?)")){
            stm.setLong(1, idTask);
            stm.setLong(2, idUser);
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
    public void setTaskInMappingByGroup(long idTask, long idGroup) {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO groups_mapping(id_group, id_task) VALUES (?,?)")){
            stm.setLong(1, idGroup);
            stm.setLong(2, idTask);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("В группе нет участников");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
//INSERT INTO users_mapping(id_task, id_user) VALUES(?,(SELECT users.id FROM users WHERE users.parent_group = ?))