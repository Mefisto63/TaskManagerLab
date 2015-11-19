package taskManager.requests.factory.genericFactory;

import taskManager.idGenerator.IdGenerator;
import taskManager.requests.factory.data.NotificationTaskData;
import taskManager.tasks.Notification;
import taskManager.tasks.Task;

/**
 * Created by sergey on 24.11.14.
 */
public class GenericNotificationFactory implements Factory<NotificationTaskData> {

    private IdGenerator idGenerator;

    public GenericNotificationFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Task createTask(NotificationTaskData data) {
        return new Notification(data.getDescription(), idGenerator.generateId());
    }

    @Override
    public Task updateData(NotificationTaskData request, Task task) {
        task.setDescription(request.getDescription());
        return task;
    }

}
