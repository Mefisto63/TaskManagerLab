package taskManager.requests.processors;

import taskManager.controller.Controller;
import taskManager.requests.type.DeleteTaskRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.ReplyResponse;

import java.io.IOException;

/**
 * Created by sergey on 10.12.14.
 */
public class DeleteTaskProcessor implements RequestProcessor<DeleteTaskRequest> {

    private Controller controller;

    public DeleteTaskProcessor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public GeneralResponse processTheRequest(DeleteTaskRequest request) {
        try {
            switch (request.getName()){
                case "Trigger":
                    controller.deleteTrigger(request.getId());
                case "Task":
                    controller.deleteTask(request.getId());
            }

            return new ReplyResponse("Задача удалена");
        } catch (IOException e) {
            return new ErrorMessageResponse(e.getMessage());

        }
    }
}
