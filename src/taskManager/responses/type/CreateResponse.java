package taskManager.responses.type;

import taskManager.TaskData;
import taskManager.tasks.Task;

/**
 * Created by sergey on 06.02.15.
 */
public class CreateResponse extends GeneralResponse {
    private TaskData task;

    public CreateResponse(TaskData task) {
        super(5);
        this.task = task;
    }

    public TaskData getTask() {
        return task;
    }
}
