package taskManager.requests.factory.genericFactory;

import taskManager.idGenerator.IdGenerator;
import taskManager.requests.factory.data.BirthdayTaskData;
import taskManager.tasks.BirthdayReminder;
import taskManager.tasks.Task;

/**
 * Created by sergey on 24.11.14.
 */
public class GenericBirthdayFactory implements Factory<BirthdayTaskData> {

    private IdGenerator idGenerator;

    public GenericBirthdayFactory (IdGenerator idGenerator)
    {this.idGenerator = idGenerator;}

    @Override
    public Task createTask(BirthdayTaskData data) {
        return new BirthdayReminder(data.getDescription(), idGenerator.generateId());
    }

    @Override
    public Task updateData(BirthdayTaskData request, Task task) {
        task.setDescription(request.getDescription());
        return task;
    }


}
