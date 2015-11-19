package taskManager.requests.processors;

import taskManager.TaskData;
import taskManager.controller.Controller;
import taskManager.requests.factory.data.NotificationTaskData;
import taskManager.requests.type.CreateTaskRequest;
import taskManager.responses.type.*;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sergey on 09.12.14.
 */
public class CreateTaskProcessor implements RequestProcessor<CreateTaskRequest>{

    private Controller controller;

    public CreateTaskProcessor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public GeneralResponse processTheRequest(CreateTaskRequest request) {

        NotificationTaskData notificationTaskData = (NotificationTaskData) request.getRequest();
        try {
            Task task = controller.createTask(request.getRequest(), new ArrayList<Long>(), new ArrayList<Long>());
            Trigger trigger = controller.createTrigger(notificationTaskData.getDate(), task.getId());
            TaskData taskData = new TaskData(trigger.getDate(),task.getDescription(),task.getName(),task.getId(),trigger.getId());
            return new CreateResponse(taskData);
        } catch (IOException e) {
            return new ErrorMessageResponse("Возникла ошибка при создании");
        }

    }
}
