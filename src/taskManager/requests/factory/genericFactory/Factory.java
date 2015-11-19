package taskManager.requests.factory.genericFactory;

import taskManager.requests.factory.data.Data;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

/**
 * Created by sergey on 24.11.14.
 */
public interface Factory<T extends Data>{
    public Task createTask(T request);

    public Task updateData(T request, Task task);

}
