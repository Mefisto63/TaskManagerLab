package taskManager.requests.factory.genericFactory;

import taskManager.requests.factory.data.TriggerData;
import taskManager.tasks.Task;

/**
 * Created by sergey on 13.02.15.
 */
public class GenericTriggerFactory implements Factory<TriggerData> {
    @Override
    public Task createTask(TriggerData request) {
        return null;
    }

    @Override
    public Task updateData(TriggerData request, Task task) {
        return null;
    }

}
