package taskManager.requests.factory.genericFactory;

import taskManager.idGenerator.IdGenerator;
import taskManager.requests.factory.data.AlarmTaskData;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.Task;

/**
 * Created by sergey on 24.11.14.
 */
public class GenericAlarmClockFactory implements Factory<AlarmTaskData> {

    private IdGenerator idGenerator;

    public GenericAlarmClockFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Task createTask(AlarmTaskData data) {
        return new AlarmClock(data.getDescription(), data.getRepeat(),idGenerator.generateId(), data.getInterval());
    }

    @Override
    public Task updateData(AlarmTaskData request, Task task) {
        AlarmClock clock = (AlarmClock) task;
        if (request.getDescription() != null)
            clock.setDescription(request.getDescription());
        if (request.getInterval() != -1)
            clock.setInterval(request.getInterval());
        if (request.getRepeat() != -1)
            clock.setRepeatCount(request.getRepeat());
        return clock;
    }


}
