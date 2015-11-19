package taskManager;

import taskManager.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 29.03.15.
 */
public class TasksPool {
    private static TasksPool ourInstance = new TasksPool();
    private List<TaskData> tasks = new ArrayList<>();

    public static TasksPool getInstance() {
        return ourInstance;
    }

    private TasksPool() {
    }

    public TaskData getTask(long idUser, long idGroup){
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                TaskData data = tasks.get(i);
                if (data.getIdUser() == idUser || data.getIdGroup() == idGroup){
                    return tasks.remove(i);
                }
            }
        }
        return null;
    }
    public void setTask(TaskData task){
        tasks.add(task);
    }
}
