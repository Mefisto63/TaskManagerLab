package taskManager.manager.dbManager.actions;

import taskManager.tasks.Task;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sergey on 18.02.15.
 */
public interface TaskActions <T extends Task> {
    Task get(ResultSet resultSet) throws SQLException;
    void insert(T task) throws IOException;
    void update(T task);
    void setTaskInMappingByUser(long idTask, long idUser);
    void setTaskInMappingByGroup(long idTask, long idGroup);
}
