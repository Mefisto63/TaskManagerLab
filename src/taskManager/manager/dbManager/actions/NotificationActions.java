package taskManager.manager.dbManager.actions;

import taskManager.connection.TMConnectionPool;
import taskManager.tasks.Notification;
import taskManager.tasks.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergey on 18.02.15.
 */
public class NotificationActions implements TaskActions<Notification> {
    private TMConnectionPool connectionPool;

    public NotificationActions(TMConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Task get(ResultSet resultSet) throws SQLException {
        Task task = null;
        String desc = resultSet.getString("description");
        long id = resultSet.getLong("id");
        task = new Notification(desc, id);
        return task;
    }

    @Override
    public void insert(Notification task) throws IOException {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement("INSERT INTO tasks(id, description, id_type) VALUES(?,?,?)")) {
            stm.setLong(1, task.getId());
            stm.setString(2, task.getDescription());
            stm.setInt(3, task.getType());
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
    public void update(Notification task) {
        Connection connection = connectionPool.get();
        try (PreparedStatement stm = connection.prepareStatement
                ("UPDATE tasks SET description = ? WHERE tasks.id = ?")) {
            stm.setLong(2, task.getId());
            stm.setString(1, task.getDescription());
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
